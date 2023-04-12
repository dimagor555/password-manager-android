package ru.dimagor555.password.domain.filter

enum class SortingType(val order: SortingOrder) {
    Title(SortingOrder.Ascending),
    RecentUsage(SortingOrder.Descending),
    FrequentUsage(SortingOrder.Descending),
    CreationDate(SortingOrder.Descending),
    EditingDate(SortingOrder.Descending)
}