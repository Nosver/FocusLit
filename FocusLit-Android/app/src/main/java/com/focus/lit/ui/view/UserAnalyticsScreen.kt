package com.focus.lit.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.focus.lit.R
import com.focus.lit.ui.viewmodel.UserAnalyticsViewModel

@Composable
fun UserAnalyticsScreen(
    navController: NavHostController,
    viewModel: UserAnalyticsViewModel = viewModel()
) {
    val streak by viewModel.streak
    val totalWorkDuration by viewModel.totalWorkDuration
    val score by viewModel.score
    val userRank by viewModel.userRank
    val achievements by viewModel.achievements

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Title with gradient
        Text(
            text = "User Analytics",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontSize = 32.sp,
                brush = Brush.horizontalGradient(
                    colors = listOf(Color(0xFF2196F3), Color(0xFF21CBF3))
                )
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Analytics Section
        SectionContainer(backgroundColor = Color(0xFFE3F2FD)) {
            AnalyticsSection(
                streak = streak,
                totalWorkDuration = totalWorkDuration,
                score = score,
                userRank = userRank
            )
        }
        Divider(
            modifier = Modifier.padding(vertical = 16.dp),
            color = Color.Gray,
            thickness = 1.dp
        )

        // Achievements Section
        SectionContainer(backgroundColor = Color(0xFFFFF3E0)) {
            AchievementsSection(achievements)
        }
        Divider(
            modifier = Modifier.padding(vertical = 16.dp),
            color = Color.Gray,
            thickness = 1.dp
        )

        // Weekly Calendar Section
        SectionContainer(backgroundColor = Color(0xFFE8F5E9)) {
            GoogleStyleWeeklyCalendar()
        }
    }
}

@Composable
fun SectionContainer(
    backgroundColor: Color,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = backgroundColor, shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        content()
    }
}

@Composable
fun AnalyticsSection(
    streak: Int,
    totalWorkDuration: Int,
    score: Int,
    userRank: Int
) {
    Column {
        AnalyticsRow(
            iconRes = R.drawable.ic_streak,
            label = "Streak: $streak"
        )
        Spacer(modifier = Modifier.height(8.dp))
        AnalyticsRow(
            iconRes = R.drawable.ic_timer,
            label = "Total Work Duration: $totalWorkDuration minutes"
        )
        Spacer(modifier = Modifier.height(8.dp))
        AnalyticsRow(
            iconRes = R.drawable.ic_timer,
            label = "Score: $score"
        )
        Spacer(modifier = Modifier.height(8.dp))
        AnalyticsRow(
            iconRes = R.drawable.ic_timer,
            label = "User Rank: $userRank"
        )
    }
}

@Composable
fun AnalyticsRow(iconRes: Int, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun AchievementsSection(achievements: List<String>) {
    Column {
        Text(text = "Achievements:", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))
        achievements.forEach { achievement ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxWidth()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFFFFC107), Color(0xFFFF9800))
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_timer),
                    contentDescription = "Achievement",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "- $achievement", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Composable
fun GoogleStyleWeeklyCalendar() {
    // Placeholder data for demonstration. Replace with real session data as needed.
    val days = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
    val sessions = listOf(2, 3, 0, 4, 1, 0, 5)

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Weekly Calendar",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        // Calendar grid: one column per day
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            days.forEachIndexed { index, day ->
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(4.dp)
                        .background(color = Color(0xFFF1F3F4), shape = RoundedCornerShape(4.dp))
                        .border(1.dp, Color.LightGray, shape = RoundedCornerShape(4.dp))
                        .padding(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = day, style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    val sessionCount = sessions[index]
                    if (sessionCount > 0) {
                        repeat(sessionCount) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(16.dp)
                                    .background(
                                        color = Color(0xFF4285F4),
                                        shape = RoundedCornerShape(2.dp)
                                    )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                        }
                    } else {
                    }
                }
            }
        }
    }
}
