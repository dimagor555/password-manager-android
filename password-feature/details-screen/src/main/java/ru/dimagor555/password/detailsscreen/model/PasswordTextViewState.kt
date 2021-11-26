package ru.dimagor555.password.detailsscreen.model

internal data class PasswordTextViewState(
    val passwordText: String = HiddenPasswordText,
    val isVisible: Boolean = false
)

internal fun createHiddenPasswordText() = PasswordTextViewState()

internal fun createVisiblePasswordText(passwordText: String) = PasswordTextViewState(
    passwordText = passwordText,
    isVisible = true
)

private const val HiddenPasswordText = "●●●●●●●●●●"