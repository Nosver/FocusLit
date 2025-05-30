package com.focus.lit.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState


@Composable
fun NavigationBarSample(navController: NavController) {
    val items = listOf(
        Screen.Homepage,
        Screen.StartSession,
        Screen.Profile,
        Screen.Goal
    )
    val selectedIcons = listOf(Icons.Filled.Home, Icons.Filled.AddCircle, Icons.Filled.Person, Icons.Filled.DateRange)
    val unselectedIcons = listOf(Icons.Outlined.Home, Icons.Outlined.AddCircle, Icons.Outlined.Person, Icons.Outlined.DateRange)
    val labels = listOf("Homepage","Start Session","Profile","Goal")
    val currentRoute = currentRoute(navController)

    NavigationBar {
        items.forEachIndexed { index, screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        if (currentRoute == screen.route) selectedIcons[index]
                        else unselectedIcons[index],
                        contentDescription = screen.route
                    )
                },
                label = { Text(labels[index]) },
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}


