package ru.dimagor555.synchronization.usecase.syncresult

import ru.dimagor555.synchronization.domain.syncresult.SyncResult
import ru.dimagor555.synchronization.usecase.syncresult.repository.SyncResultRepository

class UpdateSyncResultUseCase(
    private val syncResultRepository: SyncResultRepository,
) {
    operator fun invoke(syncResult: SyncResult) =
        syncResultRepository.updateSyncResult(syncResult)
}