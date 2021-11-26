package ru.dimagor555.password.listscreen.model

import ru.dimagor555.password.domain.FavouriteFilter
import ru.dimagor555.password.domain.PasswordFilter

internal data class FilterViewState(
    val searchText: String = "",
    val favouriteFilter: FavouriteFilter = FavouriteFilter.All
)

internal fun FilterViewState.toPasswordFilter() = PasswordFilter(
    searchText = searchText,
    favouriteFilter = favouriteFilter
)