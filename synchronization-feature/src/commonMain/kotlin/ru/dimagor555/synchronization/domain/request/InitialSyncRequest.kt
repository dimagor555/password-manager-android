package ru.dimagor555.synchronization.domain.request

import kotlinx.serialization.Serializable
import ru.dimagor555.synchronization.domain.passwordrecord.SyncPasswordRecord

@Serializable
data class InitialSyncRequest(
    val passwordRecords: List<SyncPasswordRecord> = emptyList(),
)
