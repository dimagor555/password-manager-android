package ru.dimagor555.synchronization.usecase.syncstatus

import ru.dimagor555.synchronization.domain.syncstatus.SyncStatus
import ru.dimagor555.synchronization.usecase.syncstatus.repository.SyncStatusRepository

class SetSyncStatusUseCase(
    private val syncStatusRepository: SyncStatusRepository,
) {
    operator fun invoke(syncStatus: SyncStatus) =
        syncStatusRepository.setSyncStatus(syncStatus)
}