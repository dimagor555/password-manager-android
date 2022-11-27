package ru.dimagor555.password.domain

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class PasswordMetadata(
    val isFavourite: Boolean = false,
    val creationDateTime: Instant = Clock.System.now(),
    val editingDateTime: Instant = Clock.System.now(),
    val usageHistory: UsageHistory = UsageHistory()
)
