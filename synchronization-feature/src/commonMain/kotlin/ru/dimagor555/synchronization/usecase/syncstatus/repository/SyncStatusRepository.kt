package ru.dimagor555.synchronization.usecase.syncstatus.repository

import kotlinx.coroutines.flow.Flow
import ru.dimagor555.synchronization.domain.syncstatus.SyncStatus

interface SyncStatusRepository {

    fun observeSyncStatus(): Flow<SyncStatus>

    fun setSyncStatus(syncStatus: SyncStatus)
}