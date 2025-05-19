package com.focus.lit.ui.view

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.focus.lit.R
import com.focus.lit.ui.viewmodel.HomePageViewModel
import com.focus.lit.ui.viewmodel.WeeklyWorkViewModel
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomePage(
    navController: NavHostController,
    homePageViewModel: HomePageViewModel = hiltViewModel(),
    weeklyWorkViewModel: WeeklyWorkViewModel = hiltViewModel()

) {
    val streak by homePageViewModel.streak
    val totalWorkDuration by homePageViewModel.totalWorkDuration
    val score by homePageViewModel.score
    val userRank by homePageViewModel.userRank
    val achievements by homePageViewModel.achievements
    val sessions = weeklyWorkViewModel.weeklyWorkResponse?.sessions

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
            GoogleStyleWeeklyCalendar(sessions)
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun GoogleStyleWeeklyCalendar(sessions: List<com.focus.lit.data.model.Session>?) {
    if (sessions.isNullOrEmpty()) {
        Text(
            text = "No sessions available",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxSize(),
            color = Color.Gray
        )
        return
    }

    // Filter sessions to ensure valid times
    val validSessions = sessions.filter { session ->
        val startHour = LocalDateTime.parse(session.startTime).hour
        startHour in 0..23
    }

    val days = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
    val timeSlots = listOf("0:00", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00")

    val groupedSessions = validSessions.groupBy { session ->
        LocalDateTime.parse(session.startTime).dayOfWeek.value % 7
    }

    val horizontalScrollState = rememberScrollState()

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
                        .horizontalScroll(horizontalScrollState)
                ) {
                    Spacer(modifier = Modifier.width(60.dp)) // Fixed day label space

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
                            val daySessions = groupedSessions[dayIndex] ?: emptyList()
                            daySessions.forEach { session ->
                                val startHour = LocalDateTime.parse(session.startTime).hour
                                val startOffset = startHour * 80 // Each slot is 80dp wide
                                val width = (session.workDuration / 60.0f) * 80 // Calculate width based on duration

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
                                            text = "Work", // TODO: TAG NAME
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color(0xFF4285F4),
                                            fontWeight = FontWeight.Bold,
                                            maxLines = 1
                                        )
                                        Text(
                                            text = "${session.workDuration}min",
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