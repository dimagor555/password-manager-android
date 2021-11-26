package ru.dimagor555.password.detailsscreen.model

import ru.dimagor555.core.ProgressBarState
import ru.dimagor555.core.UiComponentVisibility

internal data class PasswordDetailsViewState(
    val passwordViewState: PasswordViewState = PasswordViewState(),
    val passwordTextViewState: PasswordTextViewState = PasswordTextViewState(),
    val progressBarState: ProgressBarState = ProgressBarState.Loading,
    val removeDialogVisibility: UiComponentVisibility = UiComponentVisibility.Hide,
    val isPasswordRemoved: Boolean = false
)
