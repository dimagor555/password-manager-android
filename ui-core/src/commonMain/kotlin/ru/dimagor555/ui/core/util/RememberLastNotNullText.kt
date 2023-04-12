package ru.dimagor555.ui.core.util

import androidx.compose.runtime.*

@Composable
fun rememberLastNotNullText(text: String?): String {
    var rememberedText by remember { mutableStateOf("") }
    if (text != null) {
        rememberedText = text
    }
    return rememberedText
}