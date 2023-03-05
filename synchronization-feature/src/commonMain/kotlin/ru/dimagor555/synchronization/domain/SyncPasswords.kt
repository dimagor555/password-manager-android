package ru.dimagor555.synchronization.domain

import kotlinx.serialization.Serializable

@Serializable
data class SyncPasswords(
    var requestToUpdateRecords: List<SyncPasswordRecord>? = null,
    var requestToAddRecords: List<SyncPasswordRecord>? = null,
    var respondToUpdatePasswords: String? = null,
    var respondToAddPasswords: String? = null,
)
