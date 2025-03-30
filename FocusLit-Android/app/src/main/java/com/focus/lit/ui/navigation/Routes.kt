package com.focus.lit.ui.navigation


sealed class Screen(val route: String) {
    object Homepage : Screen("homepage")
    object StartSession : Screen("start Session")
    object Profile : Screen("profile")
    object Login : Screen("login")
    object AddTag : Screen("add_tag")
    object UserAnalytics : Screen("user_analytics")
    object Timer : Screen("timer")
    object Settings: Screen("settings")
}
