package ru.dimagor555.ui.core.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import dev.icerock.moko.resources.FontResource
import dev.icerock.moko.resources.compose.toComposeFont

internal actual fun FontResource.toComposeFont(weight: FontWeight): Font =
    this.toComposeFont(weight)
