package ru.dimagor555.password.listscreen.model

import ru.dimagor555.core.ProgressBarState
import ru.dimagor555.core.UiComponentVisibility
import ru.dimagor555.password.domain.FavouriteFilter
import ru.dimagor555.password.domain.filter.PasswordSortingType

internal sealed class PasswordListEvent {
    data class UpdateProgressBarState(val value: ProgressBarState) : PasswordListEvent()

    data class SearchTextChanged(val searchText: String) : PasswordListEvent()
    data class FavouriteFilterChanged(val favouriteFilter: FavouriteFilter) : PasswordListEvent()

    data class UpdateSortingDialogVisibility(
        val visibility: UiComponentVisibility
    ) : PasswordListEvent()

    data class SortingTypeChanged(val sortingType: PasswordSortingType) : PasswordListEvent()

    data class CopyPassword(val passwordId: Int) : PasswordListEvent()

    data class ToggleFavourite(val passwordId: Int) : PasswordListEvent()
}