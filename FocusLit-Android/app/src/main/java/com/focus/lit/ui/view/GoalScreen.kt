package com.focus.lit.ui.view

import android.widget.Toast
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.focus.lit.ui.components.GenericDropdownMenuContent
import com.focus.lit.ui.viewmodel.GoalViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalScreen(navController: NavController, viewModel: GoalViewModel = hiltViewModel()) {
    val navBackStackEntry = remember { navController.currentBackStackEntry }
    val context = LocalContext.current

    LaunchedEffect(navBackStackEntry) {
        viewModel.fetchGoals()
        viewModel.fetchTags()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.showAddGoalDialog = true },
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
                value = viewModel.searchQueryGoal,
                onValueChange = { viewModel.searchQueryGoal = it },
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
                items(viewModel.goals.filter {
                    it.tag.name.contains(viewModel.searchQueryGoal, ignoreCase = true)
                }) { goal ->
                    GoalItem(
                        tagName = goal.tag.name,
                        targetMinutes = goal.targetWorkDuration,
                        completedMinutes = goal.completedWorkDuration
                    )
                }
            }
        }
    }

    if (viewModel.showAddGoalDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.showAddGoalDialog = false },
            title = { Text("Add New Goal") },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Target Minutes Input
                    OutlinedTextField(
                        value = viewModel.targetMinutes,
                        onValueChange = {viewModel.targetMinutes = it },
                        label = { Text("Target Minutes") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = viewModel.searchQueryTag,
                        onValueChange = { viewModel.updateSearchQuery(it) },
                        label = { Text("Select Parent Tag") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    GenericDropdownMenuContent(options = viewModel.filteredTags, query = viewModel.searchQueryTag){
                        viewModel.selectParentTag(it)
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {viewModel.createGoal({
                        Toast.makeText(context, "Tag created successfully", Toast.LENGTH_SHORT).show()
                        viewModel.showAddGoalDialog = false
                    });},
                    enabled = viewModel.parentTag.isNotEmpty() && viewModel.targetMinutes.isNotEmpty()
                ) {
                    Text("Add Goal")
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.showAddGoalDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    if (viewModel.loading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f)),
            contentAlignment = Alignment.Center  // Correct placement
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(color = Color.White)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Loading...", color = Color.White, fontSize = 16.sp)
            }
        }
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
