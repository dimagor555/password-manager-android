package ru.dimagor555.password.domain.filter

import ru.dimagor555.password.domain.Password

internal class PasswordSorter(
    private val type: PasswordSortingType
) {
    fun sortPassword(passwords: List<Password>) = passwords.sortedWith(createComparator())

    private fun createComparator(): Comparator<Password> {
        val selector = createCompareSelectorByType()
        return when (type.order) {
            SortingOrder.Ascending -> compareBy(selector)
            SortingOrder.Descending -> compareByDescending(selector)
        }
    }

    private fun createCompareSelectorByType(): ((Password) -> Comparable<*>) {
        return when (type) {
            PasswordSortingType.Title -> { it -> it.title }
            PasswordSortingType.RecentUsage -> { it -> it.metadata.usageHistory.lastUsageDateTime }
            PasswordSortingType.FrequentUsage -> { it -> it.metadata.usageHistory.usagesCount }
            PasswordSortingType.CreationDate -> { it -> it.metadata.creationDateTime }
            PasswordSortingType.EditingDate -> { it -> it.metadata.editingDateTime }
        }
    }
}