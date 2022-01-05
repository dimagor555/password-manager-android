package ru.dimagor555.ui.core.model

import me.aartikov.sesame.localizedstring.LocalizedString

sealed class FieldState {
    abstract val text: String
    abstract val error: LocalizedString?

    data class Text(
        override val text: String = "",
        override val error: LocalizedString? = null
    ) : FieldState()

    data class Password(
        override val text: String = "",
        override val error: LocalizedString? = null,
        val isVisible: Boolean = false
    ) : FieldState()
}

val FieldState.isError
    get() = error != null

fun FieldState.Password.toggleVisibility() = copy(isVisible = !isVisible)

fun FieldState.copy(
    text: String = this.text,
    error: LocalizedString? = this.error
) = when (this) {
    is FieldState.Text -> copy(text = text, error = error)
    is FieldState.Password -> copy(text = text, error = error)
}