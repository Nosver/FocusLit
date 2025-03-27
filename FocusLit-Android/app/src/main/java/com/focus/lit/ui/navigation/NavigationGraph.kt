package com.focus.lit.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.focus.lit.ui.view.AddTagScreen
import com.focus.lit.ui.view.HomepageScreen
import com.focus.lit.ui.view.LoginScreen
import com.focus.lit.ui.view.ProfileScreen
import com.focus.lit.ui.view.StartSessionScreen
import com.focus.lit.ui.view.TimerScreen
import com.focus.lit.ui.view.UserAnalyticsScreen

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.Login.route) {
        composable(Screen.Homepage.route) { HomepageScreen(navController) }
        composable(Screen.StartSession.route) { StartSessionScreen(navController) }
        composable(Screen.Profile.route) { ProfileScreen(navController) }
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.AddTag.route){AddTagScreen(navController)}
        composable(Screen.UserAnalytics.route){UserAnalyticsScreen(navController)}
        composable(
            route = "timer?study={study}&break={break}&topic={topic}",
            arguments = listOf(
                navArgument("study") { type = NavType.IntType },
                navArgument("break") { type = NavType.IntType },
                navArgument("topic") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            TimerScreen(navController, backStackEntry)
        }

           }
}

