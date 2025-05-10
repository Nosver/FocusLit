package com.focus.lit.ui.view

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

data class Goal(
    val tagName: String,
    val targetMinutes: Int,
    val completedMinutes: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalScreen(navController: NavController) {
    var showAddGoalDialog by remember { mutableStateOf(false) }
    var selectedTag by remember { mutableStateOf("") }
    var targetMinutes by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }

    // Mock data
    val mockTags = listOf("Mathematics", "Physics", "Chemistry", "Biology", "History", "Literature")
    var goals by remember { mutableStateOf(
        listOf(
            Goal("Mathematics", 120, 45),
            Goal("Physics", 90, 60),
            Goal("Chemistry", 60, 30)
        )
    ) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Study Goals", fontWeight = FontWeight.Bold, fontSize = 22.sp) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddGoalDialog = true },
                containerColor = MaterialTheme.colorScheme.secondary
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add Goal", tint = Color.White)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search Goals") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(goals.filter { 
                    it.tagName.contains(searchQuery, ignoreCase = true) 
                }) { goal ->
                    GoalItem(
                        tagName = goal.tagName,
                        targetMinutes = goal.targetMinutes,
                        completedMinutes = goal.completedMinutes
                    )
                }
            }
        }
    }

    if (showAddGoalDialog) {
        AlertDialog(
            onDismissRequest = { showAddGoalDialog = false },
            title = { Text("Add New Goal") },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Tag Selection Dropdown
                    ExposedDropdownMenuBox(
                        expanded = false,
                        onExpandedChange = { },
                    ) {
                        OutlinedTextField(
                            value = selectedTag.ifEmpty { "Select a Tag" },
                            onValueChange = { },
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = false) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                        )

                        ExposedDropdownMenu(
                            expanded = false,
                            onDismissRequest = { }
                        ) {
                            mockTags.forEach { tag ->
                                DropdownMenuItem(
                                    text = { Text(tag) },
                                    onClick = { selectedTag = tag }
                                )
                            }
                        }
                    }

                    // Target Minutes Input
                    OutlinedTextField(
                        value = targetMinutes,
                        onValueChange = { if (it.isEmpty() || it.toIntOrNull() ?: 0 > 0) targetMinutes = it },
                        label = { Text("Target Minutes") },
                        keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                            keyboardType = androidx.compose.ui.text.input.KeyboardType.Number
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (selectedTag.isNotEmpty() && targetMinutes.isNotEmpty()) {
                            goals = goals + Goal(
                                tagName = selectedTag,
                                targetMinutes = targetMinutes.toInt(),
                                completedMinutes = 0
                            )
                            showAddGoalDialog = false
                            selectedTag = ""
                            targetMinutes = ""
                        }
                    },
                    enabled = selectedTag.isNotEmpty() && targetMinutes.isNotEmpty()
                ) {
                    Text("Add Goal")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddGoalDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun GoalItem(
    tagName: String,
    targetMinutes: Int,
    completedMinutes: Int
) {
    val progress = if (targetMinutes > 0) (completedMinutes.toFloat() / targetMinutes) else 0f

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .animateContentSize(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = tagName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(4.dp)
                    )
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "$completedMinutes / $targetMinutes minutes",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
