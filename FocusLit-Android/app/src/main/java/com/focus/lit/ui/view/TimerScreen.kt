package com.focus.lit.ui.view

import android.os.SystemClock
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.navigation.NavController
import kotlinx.coroutines.delay


@Composable
fun TimerScreen(navController: NavController) {
    var showDialog by remember { mutableStateOf(false) }

    // Intercept system back press
    BackHandler {
        showDialog = true
    }

    // Show confirmation dialog
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Quit Session?") },
            text = { Text("Are you sure you want to quit the session and return to Home? (you will lose all of your progress in this session) ") },
            confirmButton = {
                Button(onClick = {
                    showDialog = false
                    navController.navigate("homepage") {
                        popUpTo(0) { inclusive = true } // clear back stack
                    }
                }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("No")
                }
            }
        )
    }

    // Your timer
    AdvancedTimer(totalSeconds = 60)
}

@Composable
@Preview
fun TimerDemo() {
    AdvancedTimer(totalSeconds = 90)
}

@Composable
fun AdvancedTimer(
    totalSeconds: Int,
    modifier: Modifier = Modifier
) {
    var remainingTime by remember { mutableStateOf(totalSeconds) }
    var isRunning by remember { mutableStateOf(false) }
    var rawProgress by remember { mutableFloatStateOf(1f) }

    val context = LocalContext.current

    // Smooth animated progress
    val animatedProgress by animateFloatAsState(
        targetValue = rawProgress,
        animationSpec = tween(durationMillis = 500),
        label = "Progress Animation"
    )

    LaunchedEffect(isRunning) {
        if (isRunning) {
            val startTime = SystemClock.elapsedRealtime()
            val endTime = startTime + remainingTime * 1000

            while (SystemClock.elapsedRealtime() < endTime && isRunning) {
                val now = SystemClock.elapsedRealtime()
                val secondsLeft = ((endTime - now) / 1000).toInt()
                remainingTime = maxOf(0, secondsLeft)
                rawProgress = secondsLeft / totalSeconds.toFloat()
                delay(250L)
            }

            if (isRunning) {
                remainingTime = 0
                rawProgress = 0f
                isRunning = false
                Toast.makeText(context, "Timer Finished!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(contentAlignment = Alignment.Center) {
            // Background ring
            CircularProgressIndicator(
                progress = { 1f },
                modifier = Modifier.size(200.dp),
                color = Color.LightGray,
                strokeWidth = 12.dp
            )

            // Foreground ring, rotated to start from top
            CircularProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .size(200.dp),

                color = if (isRunning) Color.Green else Color.Gray,
                strokeWidth = 12.dp
            )

            // Time (HH:MM:SS)
            val hours = remainingTime / 3600
            val minutes = (remainingTime % 3600) / 60
            val seconds = remainingTime % 60

            Text(
                text = String.format("%02d:%02d:%02d", hours, minutes, seconds),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row {
            Button(
                onClick = { isRunning = true },
                enabled = !isRunning
            ) {
                Text("Start")
            }

            Spacer(modifier = Modifier.width(16.dp))

//            Button(
//                onClick = {
//                    isRunning = false
//                    remainingTime = totalSeconds
//                    rawProgress = 1f
//                },
//                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
//            ) {
//                Text("Reset")
//            }
        }
    }
}
