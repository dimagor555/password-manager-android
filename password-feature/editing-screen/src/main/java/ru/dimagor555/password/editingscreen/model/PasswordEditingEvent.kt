package ru.dimagor555.password.editingscreen.model

import ru.dimagor555.core.UiComponentVisibility

internal sealed class PasswordEditingEvent {
    data class UpdateSaveDialogVisibility(
        val visibility: UiComponentVisibility
    ) : PasswordEditingEvent()

    object OnExitScreenRequest : PasswordEditingEvent()
}