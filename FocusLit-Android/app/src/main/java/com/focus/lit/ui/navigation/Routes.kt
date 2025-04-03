sealed class Screen(val route: String) {
    object Homepage : Screen("homepage")
    object StartSession : Screen("start_session")
    object Profile : Screen("profile")
    object Login : Screen("login")
    object Register : Screen("register")  // Register için
    object AddTag : Screen("add_tag")
    object Timer : Screen("timer")
    object Settings: Screen("settings")
    object AccountSettings: Screen("accountSettings")
}
