package com.focus.lit.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.focus.lit.R
import androidx.compose.ui.geometry.Offset
import androidx.navigation.NavController

@Composable
fun LoginScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.login_image),
            contentDescription = "Login Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .blur(16.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Login",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 36.sp,
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.5f),
                        offset = Offset(4f, 4f),
                        blurRadius = 12f
                    )
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(0.5f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(0.5f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Text("Do not have account? ")
                Text(
                    "Register Here",
                    modifier = Modifier.clickable(onClick = { navController.navigate("Register") })
                )
            }
                Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    handleLogin(navController)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Login")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { handleGoogleLogin() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text("Enter with Google")
            }
        }
    }
}

fun handleLogin(navController: NavController) {
    navController.navigate("homepage") {
        popUpTo("login") { inclusive = true } // Removes login from back stack
    }
}

fun handleGoogleLogin() {
    throw NotImplementedError("Google login is not implemented yet")
}