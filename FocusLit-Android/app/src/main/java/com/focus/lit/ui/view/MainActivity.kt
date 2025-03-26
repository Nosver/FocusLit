package com.focus.lit.ui.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.focus.lit.ui.navigation.NavigationBarSample
import com.focus.lit.utils.FocusLitMobileTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FocusLitMobileTheme {
                val navController = rememberNavController()
                val currentBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = currentBackStackEntry?.destination?.route

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding(),
                    bottomBar = {
                        if (currentRoute != "login") {
                            NavigationBarSample(navController)
                        }
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NavigationGraph(navController)
                    }
                }
            }
        }
    }
}

sealed class Screen(val route: String) {
    object Homepage : Screen("homepage")
    object StartSession : Screen("start_session")
    object Profile : Screen("profile")
    object Login : Screen("login")
}


@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.Login.route) {
        composable(Screen.Homepage.route) { HomepageScreen(navController) }
        composable(Screen.StartSession.route) { StartSessionScreen(navController) }
        composable(Screen.Profile.route) { ProfileScreen(navController) }
        composable(Screen.Login.route) { LoginScreen(navController) }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FocusLitMobileTheme {
        Greeting("Android")
    }
}