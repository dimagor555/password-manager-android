package ru.dimagor555.password.editingcore.model

internal data class PasswordEditingViewState(
    val titleState: FieldViewState.Text = FieldViewState.Text(),
    val loginState: FieldViewState.Text = FieldViewState.Text(),
    val passwordState: FieldViewState.Password = FieldViewState.Password(),
    val isEditingFinished: Boolean = false
)

internal fun PasswordEditingViewState.toPasswordEditingDto() = PasswordEditingDto(
    title = titleState.text,
    login = loginState.text,
    password = passwordState.text
)

internal fun PasswordEditingDto.toPasswordEditingViewState() = PasswordEditingViewState(
    titleState = FieldViewState.Text(text = title),
    loginState = FieldViewState.Text(text = login),
    passwordState = FieldViewState.Password(text = password),
)