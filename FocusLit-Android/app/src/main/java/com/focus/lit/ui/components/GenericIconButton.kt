package com.focus.lit.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun GenericIconButton(
    onClick: () -> Unit = {},
    icon: ImageVector = Icons.Filled.Favorite,
    contentDescription: String = "Icon Button",
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = { onClick() },
        modifier = modifier
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription
        )
    }
}
