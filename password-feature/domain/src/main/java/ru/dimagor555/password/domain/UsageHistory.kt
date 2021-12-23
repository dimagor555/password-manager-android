package ru.dimagor555.password.domain

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class UsageHistory(
    val usagesCount: Int = 0,
    val lastUsageDateTime: Instant = Instant.DISTANT_PAST
)

fun UsageHistory.plusUsage() = copy(
    usagesCount = usagesCount + 1,
    lastUsageDateTime = Clock.System.now()
)
