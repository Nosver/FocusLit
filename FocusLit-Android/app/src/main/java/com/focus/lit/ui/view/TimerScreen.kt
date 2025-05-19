package com.focus.lit.ui.view

import android.annotation.SuppressLint
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.focus.lit.data.model.Tag
import com.focus.lit.ui.viewmodel.EndSessionViewModel
import com.focus.lit.ui.viewmodel.SessionViewModel
import kotlinx.coroutines.delay


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun TimerScreen(
    navController: NavController,
    backStackEntry: NavBackStackEntry,
    viewModel: EndSessionViewModel = hiltViewModel(),
    sessionViewModel: SessionViewModel = hiltViewModel()
) {
    val studyMinutes = backStackEntry.arguments?.getInt("study") ?: 25
    val breakMinutes = backStackEntry.arguments?.getInt("break") ?: 5
    val selectedTopic = backStackEntry.arguments?.getString("topic") ?: "General"
    val tagId = backStackEntry.arguments?.getString("id")
    val sessionId = backStackEntry.arguments?.getString("sessionId")

    Log.d("NavigationArgs", "tagId: $tagId, sessionId: $sessionId")

    var isStudyTime by remember { mutableStateOf(true) }
    var timeRemaining by remember { mutableStateOf(studyMinutes * 60) }
    var totalDuration by remember { mutableStateOf(studyMinutes * 60) }
    var showQuitDialog by remember { mutableStateOf(false) }
    var showContinueDialog by remember { mutableStateOf(false) }
    var completedMinutes by remember { mutableStateOf(0) }
    var elapsedSeconds by remember { mutableStateOf(0) }
    val context = LocalContext.current

    BackHandler {
        showQuitDialog = true
    }

    // Quit Session Dialog
    if (showQuitDialog) {
        AlertDialog(
            onDismissRequest = { showQuitDialog = false },
            title = { Text("Quit Session?") },
            text = { Text("Are you sure you want to quit the session and return to Home? (you will lose your progress in this session)") },
            confirmButton = {
                Button(
                    onClick = {
                        completedMinutes += elapsedSeconds / 60
                        elapsedSeconds = 0

                        sessionId?.toIntOrNull()?.let { id ->
                            Log.d("completed", "$completedMinutes")
                            viewModel.endSession(id, completedMinutes)
                            Toast.makeText(context, "Session ended successfully", Toast.LENGTH_SHORT).show()
                        } ?: run {
                            Toast.makeText(context, "Error: Invalid session ID", Toast.LENGTH_SHORT).show()
                        }

                        showQuitDialog = false
                        navController.navigate("homepage") {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    enabled = !viewModel.isLoading.value
                ) {
                    if (viewModel.isLoading.value) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text("Yes")
                    }
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showQuitDialog = false },
                    enabled = !viewModel.isLoading.value
                ) {
                    Text("No")
                }
            }
        )
    }

    // Continue Session Dialog
    if (showContinueDialog) {
        AlertDialog(
            onDismissRequest = { showContinueDialog = false },
            title = { Text("Continue Session?") },
            text = { Text("Do you want to continue with the same study and break times?") },
            confirmButton = {
                Button(onClick = {
                    completedMinutes += elapsedSeconds / 60
                    elapsedSeconds = 0

                    sessionId?.toIntOrNull()?.let { id ->
                        viewModel.endSession(id, completedMinutes)
                    }

                    // Create a new session with the same parameters
                    sessionViewModel.onStudyMinutesChange(studyMinutes.toString())
                    sessionViewModel.onBreakMinutesChange(breakMinutes.toString())
                    sessionViewModel.onSelectedTopicChange(selectedTopic)
                    tagId?.toIntOrNull()?.let { id ->
                        sessionViewModel.onSelectedTagChange(Tag(id, selectedTopic))
                    }

                    sessionViewModel.createSession(
                        onSuccess = {
                            showContinueDialog = false
                            isStudyTime = true
                            totalDuration = studyMinutes * 60
                            timeRemaining = studyMinutes * 60
                        },
                        onError = { errorMessage ->
                            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                            showContinueDialog = false
                            navController.navigate("homepage") {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    )
                }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    completedMinutes += elapsedSeconds / 60
                    elapsedSeconds = 0

                    sessionId?.toIntOrNull()?.let { id ->
                        viewModel.endSession(id, completedMinutes)
                    }

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
    LaunchedEffect(timeRemaining, isStudyTime) {
        if (timeRemaining > 0) {
            delay(1000L)
            timeRemaining -= 1

            if (isStudyTime) {
                elapsedSeconds += 1
            }
        } else {
            if (isStudyTime) {
                completedMinutes += elapsedSeconds / 60
                elapsedSeconds = 0
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
            progressColor = if (isStudyTime) Color.Green else Color.Blue
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

