package ru.dimagor555.password.editingcore.model

internal sealed class FieldViewState {
    abstract val text: String
    abstract val error: TextFieldError?

    data class Text(
        override val text: String = "",
        override val error: TextFieldError? = null
    ) : FieldViewState()

    data class Password(
        override val text: String = "",
        override val error: TextFieldError? = null,
        val isVisible: Boolean = false
    ) : FieldViewState()
}