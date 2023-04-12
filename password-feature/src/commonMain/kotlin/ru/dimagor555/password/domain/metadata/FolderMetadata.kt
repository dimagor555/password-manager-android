package ru.dimagor555.password.domain.metadata

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class FolderMetadata(
    val creationDateTime: Instant = Clock.System.now(),
    val editingDateTime: Instant = Clock.System.now(),
)
