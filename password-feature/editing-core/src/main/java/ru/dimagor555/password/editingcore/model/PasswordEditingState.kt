package ru.dimagor555.password.editingcore.model

internal data class PasswordEditingState(
    val titleState: FieldState.Text = FieldState.Text(),
    val loginState: FieldState.Text = FieldState.Text(),
    val passwordState: FieldState.Password = FieldState.Password(),
    val isSavingStarted: Boolean = false,
    val isExitFromScreen: Boolean = false
)

internal fun PasswordEditingState.toPasswordEditingDto() = PasswordEditingDto(
    title = titleState.text,
    login = loginState.text,
    password = passwordState.text
)

internal fun PasswordEditingDto.toPasswordEditingViewState() = PasswordEditingState(
    titleState = FieldState.Text(text = title),
    loginState = FieldState.Text(text = login),
    passwordState = FieldState.Password(text = password),
)