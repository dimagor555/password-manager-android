package ru.dimagor555.password.domain.filter

import ru.dimagor555.password.domain.FavouriteFilter

data class PasswordFilterState(
    val searchText: String = "",
    val favouriteFilter: FavouriteFilter = FavouriteFilter.All,
    val sortingType: PasswordSortingType = PasswordSortingType.Title
)