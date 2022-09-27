package ru.dimagor555.password.domain.filter

enum class PasswordSortingType(val order: SortingOrder) {
    Title(SortingOrder.Ascending),
    RecentUsage(SortingOrder.Descending),
    FrequentUsage(SortingOrder.Descending),
    CreationDate(SortingOrder.Descending),
    EditingDate(SortingOrder.Descending)
}