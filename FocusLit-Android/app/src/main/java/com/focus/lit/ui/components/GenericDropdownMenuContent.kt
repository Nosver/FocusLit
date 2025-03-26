package com.focus.lit.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GenericDropdownMenuContent(
    options: List<String>,
    query: String,
    onSelect: (String) -> Unit
) {
    if (query.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 150.dp)
                .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(8.dp))
                .border(1.dp, MaterialTheme.colorScheme.outline, shape = RoundedCornerShape(8.dp))
        ) {
            items(options) { topic ->
                DropdownMenuItem(
                    text = { Text(topic) },
                    onClick = { onSelect(topic) }
                )
            }
        }
    }
}
