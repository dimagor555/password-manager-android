package ru.dimagor555.password.editingcore.model

data class PasswordEditingViewState(
    val titleState: TextFieldViewState = TextFieldViewState(),
    val loginState: TextFieldViewState = TextFieldViewState(),
    val passwordState: TextFieldViewState = TextFieldViewState(),
    val isPasswordVisible: Boolean = false
)