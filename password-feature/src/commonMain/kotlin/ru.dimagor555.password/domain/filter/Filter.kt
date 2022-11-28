package ru.dimagor555.password.domain.filter

class Filter(
    private val state: FilterState
) {
    fun filter(params: List<FilterParams>): List<String?> = params
        .filterItems()
        .sortItems()
        .map { it.id }

    private fun List<FilterParams>.filterItems(): List<FilterParams> =
        FilterMatcher(state.searchText, state.favouriteFilter).filterPasswords(this)

    private fun List<FilterParams>.sortItems(): List<FilterParams> =
        Sorter(state.sortingType).sortParams(this)
}
