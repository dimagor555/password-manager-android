package ru.dimagor555.password.listscreen.model

import ru.dimagor555.core.ProgressBarState
import ru.dimagor555.core.UiComponentVisibility
import ru.dimagor555.password.domain.PasswordSortingType

internal data class PasswordListViewState(
    val passwordViewStates: List<PasswordViewState> = emptyList(),
    val progressBarState: ProgressBarState = ProgressBarState.Loading,
    val filterViewState: FilterViewState = FilterViewState(),
    val sortingDialogVisibility: UiComponentVisibility = UiComponentVisibility.Hide,
    val sortingType: PasswordSortingType = PasswordSortingType.Title
)