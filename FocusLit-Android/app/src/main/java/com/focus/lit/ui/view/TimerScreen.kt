package com.focus.lit.ui.view

import android.os.SystemClock
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import kotlinx.coroutines.delay


@Composable
fun TimerScreen(navController: NavController, backStackEntry: NavBackStackEntry) {
    val studyMinutes = backStackEntry.arguments?.getInt("study") ?: 25
    val breakMinutes = backStackEntry.arguments?.getInt("break") ?: 5
    val selectedTopic = backStackEntry.arguments?.getString("topic") ?: "General"

    var isStudyTime by remember { mutableStateOf(true) }
    var timeRemaining by remember { mutableStateOf(studyMinutes * 60) }
    var totalDuration by remember { mutableStateOf(studyMinutes * 60) }
    var showContinueDialog by remember { mutableStateOf(false) }
    var showQuitDialog by remember { mutableStateOf(false) }

    // Intercept system back press
    BackHandler {
        showQuitDialog = true
    }

    // Dialogs
    if (showQuitDialog) {
        AlertDialog(
            onDismissRequest = { showQuitDialog = false },
            title = { Text("Quit Session?") },
            text = { Text("Are you sure you want to quit the session and return to Home? (you will lose all progress in this session)") },
            confirmButton = {
                Button(onClick = {
                    showQuitDialog = false
                    navController.navigate("homepage") {
                        popUpTo(0) { inclusive = true }
                    }
                }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = { showQuitDialog = false }) {
                    Text("No")
                }
            }
        )
    }

    if (showContinueDialog) {
        AlertDialog(
            onDismissRequest = { showContinueDialog = false },
            title = { Text("Continue Session?") },
            text = { Text("Do you want to continue with the same study and break times?") },
            confirmButton = {
                Button(onClick = {
                    showContinueDialog = false
                    isStudyTime = true
                    totalDuration = studyMinutes * 60
                    timeRemaining = studyMinutes * 60
                }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showContinueDialog = false
                    navController.navigate("homepage") {
                        popUpTo(0) { inclusive = true }
                    }
                }) {
                    Text("No")
                }
            }
        )
    }

    // Timer logic
    LaunchedEffect(timeRemaining) {
        if (timeRemaining > 0) {
            delay(1000L)
            timeRemaining -= 1
        } else {
            if (isStudyTime) {
                isStudyTime = false
                totalDuration = breakMinutes * 60
                timeRemaining = breakMinutes * 60
            } else {
                showContinueDialog = true
            }
        }
    }

    // UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (isStudyTime) "Study Time: $selectedTopic" else "Break Time",
            style = MaterialTheme.typography.headlineMedium,
            color = if (isStudyTime) Color(0xFF4CAF50) else Color.Blue,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        AdvancedTimer(
            totalSeconds = totalDuration,
            remainingSeconds = timeRemaining,
            isRunning = true,
            modifier = Modifier.size(300.dp),
            progressColor= if (isStudyTime) Color.Green else Color.Blue
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = if (isStudyTime) "Focus on your study!" else "Take a short break and relax.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )
    }
}



@Composable
fun AdvancedTimer(
    totalSeconds: Int,
    remainingSeconds: Int,
    isRunning: Boolean,
    modifier: Modifier = Modifier,
    progressColor: Color
) {
    val progress = if (totalSeconds == 0) 0f else remainingSeconds / totalSeconds.toFloat()
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 500),
        label = "Progress Animation"
    )

    val hours = remainingSeconds / 3600
    val minutes = (remainingSeconds % 3600) / 60
    val seconds = remainingSeconds % 60

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {

        CircularProgressIndicator(
            progress = { 1f },
            color = Color.LightGray,
            strokeWidth = 16.dp,
            modifier = Modifier.fillMaxSize()
        )

        // Foreground animated ring
        CircularProgressIndicator(
            progress = { animatedProgress },
            color = if (isRunning) progressColor else Color.Gray,
            strokeWidth = 16.dp,
            modifier = Modifier.fillMaxSize()
        )

        // Centered time text
        Text(
            text = String.format("%02d:%02d:%02d", hours, minutes, seconds),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

