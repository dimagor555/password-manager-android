package ru.dimagor555.password.domain.metadata

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class PasswordMetadata(
    val isFavourite: Boolean = false,
    val creationDateTime: Instant = Clock.System.now(),
    val editingDateTime: Instant = Clock.System.now(),
    val usageHistory: UsageHistory = UsageHistory(),
)
