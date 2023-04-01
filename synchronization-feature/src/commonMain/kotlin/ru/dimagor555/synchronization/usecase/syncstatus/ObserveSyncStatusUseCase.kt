package ru.dimagor555.synchronization.usecase.syncstatus

import kotlinx.coroutines.flow.Flow
import ru.dimagor555.synchronization.domain.syncstatus.SyncStatus
import ru.dimagor555.synchronization.usecase.syncstatus.repository.SyncStatusRepository

class ObserveSyncStatusUseCase(
    private val syncStatusRepository: SyncStatusRepository,
) {
    operator fun invoke(): Flow<SyncStatus> = syncStatusRepository.observeSyncStatus()
}