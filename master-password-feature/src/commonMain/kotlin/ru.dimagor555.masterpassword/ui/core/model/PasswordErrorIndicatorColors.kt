package ru.dimagor555.masterpassword.ui.core.model

import androidx.compose.ui.graphics.Color

internal data class PasswordErrorIndicatorColors(
    val lockColor: Color,
    val bgColor: Color
) {
    companion object {
        fun chooseColors(isError: Boolean, isLightTheme: Boolean) = when (isError) {
            true -> ThemePasswordErrorIndicatorColors.Error
            false -> ThemePasswordErrorIndicatorColors.NoError
        }.getColors(isLightTheme)
    }
}