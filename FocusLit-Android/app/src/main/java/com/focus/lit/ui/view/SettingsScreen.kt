package com.focus.lit.ui.view

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun CategoryItem(title: String, icon: ImageVector, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            Icon(
                icon,
                contentDescription = null,
                modifier = Modifier.size(28.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )
            Text(title, style = MaterialTheme.typography.bodyLarge)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {
    val listState = rememberLazyListState()
    val hasScrolled by remember {
        derivedStateOf {
            listState.firstVisibleItemScrollOffset > 0
        }
    }
    val appBarElevation by animateDpAsState(targetValue = if (hasScrolled) 4.dp else 0.dp, label = "elevation")

    var isNotifiable by remember { mutableStateOf(true) }

    var darkTheme by remember { mutableStateOf(true) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = if (isSystemInDarkTheme()) {
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = if (hasScrolled) 1f else 0f)
                    } else {
                        MaterialTheme.colorScheme.surface
                    },
                ),
                modifier = Modifier.shadow(appBarElevation),
                title = { Text(text = "Settings") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("profile") }) {
                        Icon(Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = "Go back")
                    }
                },
            )
        },
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
            LazyColumn(
                contentPadding = padding,
                modifier = Modifier.widthIn(max = 600.dp),
                state = listState
            ) {
                item {
                    CategoryItem(
                        title = "Change Password",
                        icon = Icons.Outlined.AccountCircle,
                        onClick = { navController.navigate("accountSettings") })
                }

                item {
                    SwitchableItem(
                        title = "Notifications",
                        icon = Icons.Outlined.Notifications,
                        checked = isNotifiable,
                        onCheckedChange = { isNotifiable = it }
                    )
                }
                item {
                    SwitchableItem(
                        title = "Dark Theme",
                        icon = Icons.Outlined.Create,
                        checked = darkTheme,
                        onCheckedChange = { darkTheme = it }
                    )
                }
                item { HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp)) }
                item {
                    CategoryItem(
                        title = "Privacy Policy",
                        icon = Icons.Outlined.Lock,
                        onClick = { /*TODO*/ })
                }
                item {
                    CategoryItem(
                        title = "Send Feedback",
                        icon = Icons.Outlined.Email,
                        onClick = { /*TODO*/ })
                }
                item {
                    CategoryItem(
                        title = "Report a Bug",
                        icon = Icons.Outlined.Warning,
                        onClick = { /*TODO*/ })
                }

            }
        }
    }
}

@Composable
fun SwitchableItem(title: String, icon: ImageVector, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                icon,
                contentDescription = null,
                modifier = Modifier.size(28.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.width(30.dp))
            Text(title, style = MaterialTheme.typography.bodyLarge)
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}
