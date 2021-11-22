package ru.dimagor555.ui.core.components

import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun SimpleDropdownMenuItem(text: String, onClick: () -> Unit) {
    DropdownMenuItem(onClick = onClick) {
        Text(
            text = text,
            style = MaterialTheme.typography.body1
        )
    }
}