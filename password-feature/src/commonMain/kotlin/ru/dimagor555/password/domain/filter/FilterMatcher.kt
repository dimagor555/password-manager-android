package ru.dimagor555.password.domain.filter

internal class FilterMatcher(
    searchText: String,
    private val favouriteFilter: FavouriteFilter
) {
    private val searchText = searchText.trim()

    fun filterPasswords(params: List<FilterParams>) = params.filter { matches(it) }

    private fun matches(params: FilterParams) =
        favouriteFilter.matches(params) && matchesSearchText(params)

    private fun matchesSearchText(params: FilterParams): Boolean = params.searchValues.any {
        searchText.isBlank() || it.contains(searchText, ignoreCase = true)
    }
}
