package ru.dimagor555.ui.core.component.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SimpleButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    enabled: Boolean = true,
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        contentPadding = contentPadding,
        enabled = enabled,
    ) {
        Text(text = text)
    }
}