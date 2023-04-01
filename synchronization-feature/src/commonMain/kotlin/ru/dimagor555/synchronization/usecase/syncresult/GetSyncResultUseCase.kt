package ru.dimagor555.synchronization.usecase.syncresult

import ru.dimagor555.synchronization.domain.syncresult.SyncResult
import ru.dimagor555.synchronization.usecase.syncresult.repository.SyncResultRepository

class GetSyncResultUseCase(
    private val syncResultRepository: SyncResultRepository,
) {
    operator fun invoke(): SyncResult = syncResultRepository.getSyncResult()
}