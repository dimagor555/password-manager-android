package ru.dimagor555.password.domain

import ru.dimagor555.core.SortingOrder

data class PasswordSorting(
    private val type: PasswordSortType,
    private val order: SortingOrder
) {
    val comparator = createComparator()

    private fun createComparator(): Comparator<Password> {
        val comparator = createComparatorByType()
        return when (order) {
            SortingOrder.Ascending -> comparator
            SortingOrder.Descending -> comparator.reversed()
        }
    }

    private fun createComparatorByType(): Comparator<Password> {
        return when (type) {
            PasswordSortType.Title -> compareBy { it.encryptedPassword.login }
            PasswordSortType.RecentUsage -> compareBy { it.usageHistory.lastUsageDateTime }
            PasswordSortType.FrequentUsage -> compareBy { it.usageHistory.usagesCount }
            PasswordSortType.CreationDate -> compareBy { it.dateTimeMetadata.creationDateTime }
            PasswordSortType.EditDate -> compareBy { it.dateTimeMetadata.editingDateTime }
        }
    }
}