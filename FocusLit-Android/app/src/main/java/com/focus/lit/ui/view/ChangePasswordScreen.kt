package com.focus.lit.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.text.input.PasswordVisualTransformation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordScreen(navController: NavController) {
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Change Password") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = "Go back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PasswordTextField(
                label = "Current Password",
                password = currentPassword,
                onPasswordChange = { currentPassword = it },
                passwordVisible = passwordVisible,
                onVisibilityToggle = { passwordVisible = !passwordVisible }
            )
            Spacer(modifier = Modifier.height(12.dp))
            PasswordTextField(
                label = "New Password",
                password = newPassword,
                onPasswordChange = { newPassword = it },
                passwordVisible = passwordVisible,
                onVisibilityToggle = { passwordVisible = !passwordVisible }
            )
            Spacer(modifier = Modifier.height(12.dp))
            PasswordTextField(
                label = "Confirm Password",
                password = confirmPassword,
                onPasswordChange = { confirmPassword = it },
                passwordVisible = passwordVisible,
                onVisibilityToggle = { passwordVisible = !passwordVisible }
            )
            errorMessage?.let {
                Text(it, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 8.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                if (newPassword == confirmPassword && newPassword.isNotBlank()) {
                    // Handle password change logic
                    navController.popBackStack()
                } else {
                    errorMessage = "Passwords do not match"
                }
            }) {
                Text("Change Password")
            }
        }
    }
}

@Composable
fun PasswordTextField(label: String, password: String, onPasswordChange: (String) -> Unit, passwordVisible: Boolean, onVisibilityToggle: () -> Unit) {
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text(label) },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = onVisibilityToggle) {
               // Icon(if (passwordVisible) Icons.Outlined. else Icons.Outlined.VisibilityOff, contentDescription = "Toggle password visibility")
            }
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}
