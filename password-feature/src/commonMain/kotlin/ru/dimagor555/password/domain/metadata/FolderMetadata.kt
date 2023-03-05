package ru.dimagor555.password.domain.metadata

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class FolderMetadata(
    val creationDateTime: Instant = Clock.System.now(),
    val editingDateTime: Instant = Clock.System.now(),
)
