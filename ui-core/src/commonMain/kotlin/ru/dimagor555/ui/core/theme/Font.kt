package ru.dimagor555.ui.core.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import dev.icerock.moko.resources.FontResource

internal expect fun FontResource.toComposeFont(
    weight: FontWeight = FontWeight.Normal
): Font