package ru.dimagor555.password.editingcore.model

internal sealed class PasswordEditingEvent {
    data class OnTitleChanged(val title: String) : PasswordEditingEvent()
    data class OnLoginChanged(val login: String) : PasswordEditingEvent()
    data class OnPasswordChanged(val password: String) : PasswordEditingEvent()

    object TogglePasswordVisibility : PasswordEditingEvent()

    object TryFinishEditing : PasswordEditingEvent()
    object FinishEditing: PasswordEditingEvent()

    object ExitScreen: PasswordEditingEvent()
}