package ru.dimagor555.syncpassintegration.domain

import ru.dimagor555.password.domain.password.Password
import ru.dimagor555.synchronization.domain.SyncPasswordRecord

fun Password.toSyncPasswordRecord() = SyncPasswordRecord(
    id = id ?: error("Impossible"),
    editingDateTime = metadata.editingDateTime,
)

fun List<Password>.toSyncPasswordRecords() = map {
    it.toSyncPasswordRecord()
}
