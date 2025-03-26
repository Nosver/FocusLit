package com.focus.lit.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun GenericButton(
    onClick: () -> Unit = {},
    text: String = "Default",
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled
    ) {
        Text(text = text)
    }
}
