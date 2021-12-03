package ru.dimagor555.password.listscreen.model

import ru.dimagor555.core.ProgressBarState
import ru.dimagor555.core.UiComponentVisibility
import ru.dimagor555.password.domain.filter.PasswordFilterState

internal data class PasswordListViewState(
    val passwordViewStates: List<PasswordViewState> = emptyList(),
    val progressBarState: ProgressBarState = ProgressBarState.Loading,
    val filterState: PasswordFilterState = PasswordFilterState(),
    val sortingDialogVisibility: UiComponentVisibility = UiComponentVisibility.Hide
)