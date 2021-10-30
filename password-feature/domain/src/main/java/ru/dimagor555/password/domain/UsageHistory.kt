package ru.dimagor555.password.domain

import kotlinx.datetime.Instant

class UsageHistory(private val usages: List<Usage>) {
    val usagesCount
        get() = usages.size

    val lastUsageDateTime
        get() = usages.maxOfOrNull { it.datetime } ?: Instant.DISTANT_PAST
}