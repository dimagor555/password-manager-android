package ru.dimagor555.password.domain.filter

import ru.dimagor555.core.SortingOrder
import ru.dimagor555.password.domain.Password
import ru.dimagor555.password.domain.UsageHistory

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
            PasswordSortingType.RecentUsage -> { it -> UsageHistory(it.usages).lastUsageDateTime }
            PasswordSortingType.FrequentUsage -> { it -> UsageHistory(it.usages).usagesCount }
            PasswordSortingType.CreationDate -> { it -> it.creationDateTime }
            PasswordSortingType.EditingDate -> { it -> it.editingDateTime }
        }
    }
}