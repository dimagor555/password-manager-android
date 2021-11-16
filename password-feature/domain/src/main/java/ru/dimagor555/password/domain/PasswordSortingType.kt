package ru.dimagor555.password.domain

import ru.dimagor555.core.SortingOrder

enum class PasswordSortingType(val order: SortingOrder) {
    Title(SortingOrder.Ascending),
    RecentUsage(SortingOrder.Descending),
    FrequentUsage(SortingOrder.Descending),
    CreationDate(SortingOrder.Descending),
    EditDate(SortingOrder.Descending)
}