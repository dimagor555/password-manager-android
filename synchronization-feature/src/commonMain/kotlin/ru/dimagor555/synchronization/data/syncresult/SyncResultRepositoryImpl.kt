package ru.dimagor555.synchronization.data.syncresult

import kotlinx.coroutines.flow.MutableStateFlow
import ru.dimagor555.synchronization.domain.syncresult.SyncResult
import ru.dimagor555.synchronization.usecase.syncresult.repository.SyncResultRepository

class SyncResultRepositoryImpl : SyncResultRepository {

    private val _syncResult = MutableStateFlow(SyncResult())

    override fun getSyncResult(): SyncResult = _syncResult.value

    override fun updateSyncResult(syncResult: SyncResult) {
        _syncResult.value = syncResult
    }
}