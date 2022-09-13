package ru.dimagor555.password.ui.detailsscreen.model

internal data class PasswordTextState(
    val passwordText: String = HiddenPasswordText,
    val isVisible: Boolean = false
)

internal fun createHiddenPasswordText() = PasswordTextState()

internal fun createVisiblePasswordText(passwordText: String) = PasswordTextState(
    passwordText = passwordText,
    isVisible = true
)

private const val HiddenPasswordText = "●●●●●●●●●●"