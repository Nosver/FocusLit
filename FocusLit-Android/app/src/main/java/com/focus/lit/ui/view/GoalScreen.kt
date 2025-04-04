package com.focus.lit.ui.view

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalScreen(navController: NavController) {
    var newGoal by remember { mutableStateOf("") }
    var goalsList by remember { mutableStateOf(
        listOf(
            "Work with the Pomodoro technique",
            "Focus for 25 minutes, take a 5-minute break",
            "Set a goal of working for 3 hours today"
        )
    ) }
    var searchQuery by remember { mutableStateOf("") }

    fun addGoal() {
        if (newGoal.isNotEmpty()) {
            goalsList = goalsList + newGoal
            newGoal = ""
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Your Goals", fontWeight = FontWeight.Bold, fontSize = 22.sp) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { addGoal() },
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
            BasicTextField(
                value = newGoal,
                onValueChange = { newGoal = it },
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.Black, fontSize = 18.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .shadow(4.dp, shape = MaterialTheme.shapes.medium)
                    .background(Color.White, shape = MaterialTheme.shapes.medium)
                    .padding(12.dp)
            )

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

            val filteredGoals = goalsList.filter { it.contains(searchQuery, ignoreCase = true) }

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredGoals.size) { index ->
                    GoalItem(goal = filteredGoals[index])
                }
            }
        }
    }
}

@Composable
fun GoalItem(goal: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .animateContentSize(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = goal,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp, fontWeight = FontWeight.Medium)
            )
        }
    }
}
