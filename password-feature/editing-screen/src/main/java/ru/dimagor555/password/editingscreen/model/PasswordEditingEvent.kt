package ru.dimagor555.password.editingscreen.model

internal sealed class PasswordEditingEvent {
    data class UpdateSaveDialogVisibility(val visible: Boolean) : PasswordEditingEvent()

    object OnExitScreenRequest : PasswordEditingEvent()
}