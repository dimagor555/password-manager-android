package ru.dimagor555.ui.core.components

import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SimpleDropdownMenuItem(
    text: String,
    onClick: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    DropdownMenuItem(
        modifier = modifier,
        onClick = {
            onDismiss()
            onClick()
        }
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.body1
        )
    }
}