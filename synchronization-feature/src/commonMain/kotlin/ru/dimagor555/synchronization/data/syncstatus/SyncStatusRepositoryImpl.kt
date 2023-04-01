package ru.dimagor555.synchronization.data.syncstatus

import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.dimagor555.synchronization.domain.syncstatus.SyncStatus
import ru.dimagor555.synchronization.usecase.syncstatus.repository.SyncStatusRepository

class SyncStatusRepositoryImpl : SyncStatusRepository {

    private val _syncStatus: MutableStateFlow<SyncStatus> = MutableStateFlow(SyncStatus.Initial)

    override fun observeSyncStatus(): Flow<SyncStatus> =
        _syncStatus.asStateFlow()

    override fun setSyncStatus(syncStatus: SyncStatus) {
        Napier.e("SyncStatusRepositoryImpl setSyncStatus _syncStatus.value 1 = ${_syncStatus.value}")
        _syncStatus.value = syncStatus
        Napier.e("SyncStatusRepositoryImpl setSyncStatus _syncStatus.value 2 = ${_syncStatus.value}")
    }
}