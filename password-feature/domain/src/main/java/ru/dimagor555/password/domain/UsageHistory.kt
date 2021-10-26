package ru.dimagor555.password.domain

import kotlinx.datetime.Instant

data class UsageHistory(
    private val usages: MutableList<Usage> = mutableListOf()
) {
    val usagesCount
        get() = usages.size

    val lastUsageDateTime
        get() = usages.maxOfOrNull { it.datetime } ?: Instant.DISTANT_PAST

    fun addUsage(usage: Usage) {
        usages += usage
    }
}