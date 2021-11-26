package ru.dimagor555.password.detailsscreen.model

import ru.dimagor555.core.ProgressBarState
import ru.dimagor555.core.UiComponentVisibility
import ru.dimagor555.password.domain.Password

internal sealed class PasswordDetailsEvent {
    data class ShowPassword(val password: Password) : PasswordDetailsEvent()

    data class UpdateProgressBarState(val value: ProgressBarState) : PasswordDetailsEvent()

    data class UpdateRemoveDialogVisibility(
        val visibility: UiComponentVisibility
    ) : PasswordDetailsEvent()

    object TogglePasswordVisibility : PasswordDetailsEvent()

    object ToggleFavourite : PasswordDetailsEvent()

    object CopyPassword : PasswordDetailsEvent()
    object CopyLogin : PasswordDetailsEvent()

    object RemovePassword : PasswordDetailsEvent()
    object OnPasswordRemoved : PasswordDetailsEvent()
}
