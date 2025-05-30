package com.focus.lit.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.focus.lit.ui.view.AddTagScreen
import com.focus.lit.ui.view.ChangePasswordScreen
import com.focus.lit.ui.view.HomePage
import com.focus.lit.ui.view.LoginScreen
import com.focus.lit.ui.view.ProfileScreen
import com.focus.lit.ui.view.SettingsScreen
import com.focus.lit.ui.view.StartSessionScreen
import com.focus.lit.ui.view.TimerScreen
import com.focus.lit.ui.view.RegisterScreen
import com.focus.lit.ui.view.GoalScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.Login.route) {
        composable(Screen.Homepage.route) { HomePage(navController) }
        composable(Screen.StartSession.route) { StartSessionScreen(navController) }
        composable(Screen.Profile.route) { ProfileScreen(navController) }
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.Goal.route) { GoalScreen(navController) }
        composable(Screen.Register.route) { RegisterScreen(navController) }
        composable(Screen.AddTag.route){AddTagScreen(navController)}
        composable(
            route = "timer?study={study}&break={break}&topic={topic}&id={id}&sessionId={sessionId}",
            arguments = listOf(
                navArgument("study") { type = NavType.IntType },
                navArgument("break") { type = NavType.IntType },
                navArgument("topic") { type = NavType.StringType },
                navArgument("id") { type = NavType.StringType },
                navArgument("sessionId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            TimerScreen(navController, backStackEntry)
        }

            composable(Screen.Settings.route){SettingsScreen(navController)}
            composable(Screen.AccountSettings.route){ChangePasswordScreen(navController)}
    }
}

