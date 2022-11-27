package ru.dimagor555.masterpassword.ui.core.model

import androidx.compose.ui.graphics.Color

internal sealed class ThemePasswordErrorIndicatorColors(
    private val lockColor: ThemeColor,
    private val bgColor: ThemeColor
) {
    fun getColors(isLightTheme: Boolean) = PasswordErrorIndicatorColors(
        lockColor.getColor(isLightTheme),
        bgColor.getColor(isLightTheme)
    )

    object NoError : ThemePasswordErrorIndicatorColors(
        lockColor = ThemeColor(light = LightNoErrorLockColor, dark = DarkNoErrorLockColor),
        bgColor = ThemeColor(light = LightNoErrorBgColor, dark = DarkNoErrorBgColor)
    )

    object Error : ThemePasswordErrorIndicatorColors(
        lockColor = ThemeColor(light = LightErrorLockColor, dark = DarkErrorLockColor),
        bgColor = ThemeColor(light = LightErrorBgColor, dark = DarkErrorBgColor)
    )

    companion object {
        private val LightNoErrorLockColor = Color(0xFF26C6DB)
        private val DarkNoErrorLockColor = Color(0xFF0097A8)

        private val LightNoErrorBgColor = Color(0xFFE3F8FC)
        private val DarkNoErrorBgColor = Color(0xFF5CD7ED)

        private val LightErrorLockColor = Color(0xFFFF6C87)
        private val DarkErrorLockColor = Color(0xFFE73454)

        private val LightErrorBgColor = Color(0xFFFAE3E3)
        private val DarkErrorBgColor = Color(0xFFF09486)
    }
}