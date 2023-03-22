package ru.dimagor555.password.domain.filter

data class FilterState(
    val searchText: String = "",
    val favouriteFilter: FavouriteFilter = FavouriteFilter.All,
    val sortingType: SortingType = SortingType.Title
)
