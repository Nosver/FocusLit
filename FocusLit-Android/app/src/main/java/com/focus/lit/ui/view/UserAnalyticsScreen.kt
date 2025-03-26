package com.focus.lit.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.focus.lit.ui.viewmodel.UserAnalyticsViewModel

@Composable
fun UserAnalyticsScreen(navController: NavHostController, viewModel: UserAnalyticsViewModel = viewModel()) {
    val streak by viewModel.streak
    val totalWorkDuration by viewModel.totalWorkDuration
    val score by viewModel.score
    val userRank by viewModel.userRank
    val achievements by viewModel.achievements

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "User Analytics", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Streak: $streak")
        Text(text = "Total Work Duration: $totalWorkDuration minutes")
        Text(text = "Score: $score")
        Text(text = "User Rank: $userRank")
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Achievements:")
        achievements.forEach { achievement ->
            Text(text = "- $achievement")
        }
    }
}