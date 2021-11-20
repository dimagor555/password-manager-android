package ru.dimagor555.password.domain

import ru.dimagor555.core.SortingOrder

data class PasswordSorting(
    private val type: PasswordSortingType
) {
    fun createComparator(): Comparator<Password> {
        val selector = createCompareSelectorByType()
        return when (type.order) {
            SortingOrder.Ascending -> compareBy(selector)
            SortingOrder.Descending -> compareByDescending(selector)
        }
    }

    private fun createCompareSelectorByType(): ((Password) -> Comparable<*>) {
        return when (type) {
            PasswordSortingType.Title -> { it -> it.title }
            PasswordSortingType.RecentUsage -> { it -> UsageHistory(it.usages).lastUsageDateTime }
            PasswordSortingType.FrequentUsage -> { it -> UsageHistory(it.usages).usagesCount }
            PasswordSortingType.CreationDate -> { it -> it.creationDateTime }
            PasswordSortingType.EditingDate -> { it -> it.editingDateTime }
        }
    }
}