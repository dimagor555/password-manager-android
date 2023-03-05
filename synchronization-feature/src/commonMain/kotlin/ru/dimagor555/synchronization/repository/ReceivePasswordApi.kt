package ru.dimagor555.synchronization.repository

import ru.dimagor555.synchronization.domain.SyncPasswordRecord
import ru.dimagor555.synchronization.domain.SyncPasswords

interface ReceivePasswordApi {

    suspend fun getSyncPasswords(
        syncPasswordRecords: List<SyncPasswordRecord>,
    ): SyncPasswords

    suspend fun addPasswords(syncPasswords: SyncPasswords)
}
