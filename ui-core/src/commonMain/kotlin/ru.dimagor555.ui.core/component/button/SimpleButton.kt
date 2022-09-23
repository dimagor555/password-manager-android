package ru.dimagor555.ui.core.component.button

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SimpleButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier,
        onClick = onClick
    ) {
        Text(text = text)
    }
}