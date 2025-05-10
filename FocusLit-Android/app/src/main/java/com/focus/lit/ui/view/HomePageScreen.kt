package com.focus.lit.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.focus.lit.R
import com.focus.lit.ui.viewmodel.HomePageViewModel
import com.focus.lit.ui.viewmodel.ProfileViewModel

@Composable
fun HomePage(
    navController: NavHostController,
    viewModel: HomePageViewModel = hiltViewModel()
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
            text = "Welcome YOU",
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
                totalWorkDuration = totalWorkDuration.toInt(), // Convert to Int
                score = score.toInt(), // Convert to Int
                userRank = userRank
            )
        }
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 16.dp),
            thickness = 1.dp,
            color = Color.Gray
        )

        // Achievements Section
        SectionContainer(backgroundColor = Color(0xFFFFF3E0)) {
            AchievementsSection(achievements)
        }
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 16.dp),
            thickness = 1.dp,
            color = Color.Gray
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
            iconRes = R.drawable.ic_streak2,
            label = "Streak: $streak"
        )
        Spacer(modifier = Modifier.height(8.dp))
        AnalyticsRow(
            iconRes = R.drawable.ic_timer2,
            label = "Total Work Duration: $totalWorkDuration minutes"
        )
        Spacer(modifier = Modifier.height(8.dp))
        AnalyticsRow(
            iconRes = R.drawable.ic_score2,
            label = "Score: $score"
        )
        Spacer(modifier = Modifier.height(8.dp))
        AnalyticsRow(
            iconRes = R.drawable.ic_rank,
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
    val days = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
    val timeSlots = listOf("9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00")
    
    // Mock session data with start hour and duration
    val sessions = mapOf(
        0 to listOf(Session(9, 60, "Mathematics"), Session(14, 45, "Physics")),
        1 to listOf(Session(10, 30, "Chemistry"), Session(15, 60, "Biology")),
        2 to listOf(Session(11, 45, "History")),
        3 to listOf(Session(13, 60, "Literature"), Session(16, 30, "Mathematics")),
        4 to listOf(Session(9, 45, "Physics"), Session(14, 60, "Chemistry")),
        5 to listOf(),
        6 to listOf(Session(10, 60, "Biology"), Session(15, 45, "History"))
    )

    val horizontalScrollState = rememberScrollState()

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Weekly Study Schedule",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                // Time slots header
                Row(
                    modifier = Modifier
                        .horizontalScroll(horizontalScrollState)
                ) {
                    // Fixed day label space
                    Spacer(modifier = Modifier.width(60.dp))
                    
                    // Scrollable time slots
                    Row(
                        modifier = Modifier.width(80.dp * timeSlots.size)
                    ) {
                        timeSlots.forEach { time ->
                            Box(
                                modifier = Modifier.width(80.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = time,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }

                // Calendar grid
                days.forEachIndexed { dayIndex, day ->
                    Row(
                        modifier = Modifier
                            .horizontalScroll(horizontalScrollState)
                            .height(60.dp)
                            .padding(vertical = 4.dp)
                    ) {
                        // Fixed day label
                        Box(
                            modifier = Modifier
                                .width(60.dp)
                                .fillMaxHeight(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Text(
                                text = day,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        // Scrollable time slots with sessions
                        Box(
                            modifier = Modifier.width(80.dp * timeSlots.size)
                        ) {
                            // Empty time slot backgrounds
                            Row {
                                repeat(timeSlots.size) {
                                    Box(
                                        modifier = Modifier
                                            .width(80.dp)
                                            .fillMaxHeight()
                                            .padding(horizontal = 2.dp)
                                            .background(
                                                Color(0xFFF1F3F4),
                                                RoundedCornerShape(4.dp)
                                            )
                                            .border(
                                                1.dp,
                                                Color.LightGray,
                                                RoundedCornerShape(4.dp)
                                            )
                                    )
                                }
                            }

                            // Sessions
                            val daySessions = sessions[dayIndex] ?: emptyList()
                            daySessions.forEach { session ->
                                val startOffset = (session.hour - 9) * 80 // Each slot is 80dp wide
                                val width = (session.duration / 60.0f) * 80 // Calculate width based on duration

                                Box(
                                    modifier = Modifier
                                        .offset(x = startOffset.dp)
                                        .width(width.dp)
                                        .fillMaxHeight()
                                        .padding(horizontal = 2.dp)
                                        .background(
                                            Color(0xFF4285F4).copy(alpha = 0.2f),
                                            RoundedCornerShape(4.dp)
                                        )
                                        .border(
                                            1.dp,
                                            Color(0xFF4285F4),
                                            RoundedCornerShape(4.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = session.tag,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color(0xFF4285F4),
                                            fontWeight = FontWeight.Bold,
                                            maxLines = 1
                                        )
                                        Text(
                                            text = "${session.duration}min",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color(0xFF4285F4),
                                            maxLines = 1
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// Data class for session information
private data class Session(
    val hour: Int,
    val duration: Int,
    val tag: String
)
