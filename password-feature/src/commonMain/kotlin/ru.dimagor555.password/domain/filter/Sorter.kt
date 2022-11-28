package ru.dimagor555.password.domain.filter

internal class Sorter(
    private val type: SortingType
) {
    fun sortParams(params: List<FilterParams>) = params.sortedWith(createComparator())

    private fun createComparator(): Comparator<FilterParams> {
        val selector = createCompareSelectorByType()
        return when (type.order) {
            SortingOrder.Ascending -> compareBy(selector)
            SortingOrder.Descending -> compareByDescending(selector)
        }
    }

    private fun createCompareSelectorByType(): ((FilterParams) -> Comparable<*>) {
        return when (type) {
            SortingType.Title -> { it -> it.titleSortValue }
            SortingType.RecentUsage -> { it -> it.usageHistory.lastUsageDateTime }
            SortingType.FrequentUsage -> { it -> it.usageHistory.usagesCount }
            SortingType.CreationDate -> { it -> it.creationDateTime }
            SortingType.EditingDate -> { it -> it.editingDateTime }
        }
    }
}
