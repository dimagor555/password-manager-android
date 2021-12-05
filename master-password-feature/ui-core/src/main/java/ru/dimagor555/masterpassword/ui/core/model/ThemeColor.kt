package ru.dimagor555.masterpassword.ui.core.model

import androidx.compose.ui.graphics.Color

internal class ThemeColor(
    private val light: Color,
    private val dark: Color
) {
    fun getColor(isLightTheme: Boolean) = if (isLightTheme) light else dark
}