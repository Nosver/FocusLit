package com.focus.lit.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun GoalScreen(navController: NavController) {
    var newGoal by remember { mutableStateOf("") }
    var goalsList by remember { mutableStateOf(listOf<String>()) }

    fun addGoal() {
        if (newGoal.isNotEmpty()) {
            goalsList = goalsList + newGoal
            newGoal = ""
        }
    }

    fun removeGoal(goal: String) {
        goalsList = goalsList - goal
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Your Goals",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontSize = 32.sp, fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.LightGray)
        ) {
            BasicTextField(
                value = newGoal,
                onValueChange = { newGoal = it },
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.Black, fontSize = 18.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }

        Button(
            onClick = { addGoal() },
            modifier = Modifier.padding(vertical = 12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("Add Goal")
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(goalsList) { goal ->
                GoalItem(goal = goal, onRemove = { removeGoal(goal) })
            }
        }
    }
}

@Composable
fun GoalItem(goal: String, onRemove: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(4.dp)
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
                style = MaterialTheme.typography.bodyLarge
            )
            IconButton(onClick = onRemove) {
                Icon(Icons.Filled.Delete, contentDescription = "Delete Goal", tint = Color.Red)
            }
        }
    }
}
