package ru.dimagor555.password.editingcore.model

import me.aartikov.sesame.localizedstring.LocalizedString

internal sealed class FieldState {
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