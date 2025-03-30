package com.focus.lit.ui.view

import androidx.compose.animation.core.animateDpAsState
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
import androidx.compose.ui.tooling.preview.Preview
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
                        title = "Account",
                        icon = Icons.Outlined.AccountCircle,
                        onClick = { /*TODO*/ })
                }

                item {
                    CategoryItem(
                        title = "Privacy",
                        icon = Icons.Outlined.Lock,
                        onClick = { /*TODO*/ })
                }
                item {
                    CategoryItem(
                        title = "Notifications",
                        icon = Icons.Outlined.Notifications,
                        onClick = { /*TODO*/ })
                }
                item {
                    CategoryItem(
                        title = "Change Theme",
                        icon = Icons.Outlined.Create,
                        onClick = { /*TODO*/ })
                }
                item { HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp)) }
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
