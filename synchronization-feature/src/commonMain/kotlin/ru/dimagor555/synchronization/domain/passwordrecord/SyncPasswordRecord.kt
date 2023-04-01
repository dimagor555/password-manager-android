package ru.dimagor555.synchronization.domain.passwordrecord

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class SyncPasswordRecord(
    val id: String,
    val editingDateTime: Instant,
) {
    companion object {
        const val path = "syncPasswordRecords"
    }
}
