package ru.dimagor555.synchronization.usecase.syncresult.repository

import ru.dimagor555.synchronization.domain.syncresult.SyncResult

interface SyncResultRepository {

    fun getSyncResult(): SyncResult

    fun updateSyncResult(syncResult: SyncResult)
}