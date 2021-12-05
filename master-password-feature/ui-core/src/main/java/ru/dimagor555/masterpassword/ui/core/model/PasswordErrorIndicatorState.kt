package ru.dimagor555.masterpassword.ui.core.model

sealed class PasswordErrorIndicatorState(
    private val themeColors: ThemePasswordErrorIndicatorColors
) {
    internal fun getColors(isLightTheme: Boolean) = themeColors.getColors(isLightTheme)

    object NoError : PasswordErrorIndicatorState(ThemePasswordErrorIndicatorColors.NoError)
    object Error : PasswordErrorIndicatorState(ThemePasswordErrorIndicatorColors.Error)
}
