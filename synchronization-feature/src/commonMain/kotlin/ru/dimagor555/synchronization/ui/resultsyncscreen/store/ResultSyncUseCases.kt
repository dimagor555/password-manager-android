package ru.dimagor555.synchronization.ui.resultsyncscreen.store

import ru.dimagor555.synchronization.usecase.rest.StopServerUseCase
import ru.dimagor555.synchronization.usecase.rest.repository.ServerRepository
import ru.dimagor555.synchronization.usecase.syncresult.GetSyncResultUseCase
import ru.dimagor555.synchronization.usecase.syncresult.repository.SyncResultRepository

class ResultSyncUseCases(
    syncResultRepository: SyncResultRepository,
    serverRepository: ServerRepository,
) {
    val getSyncResult = GetSyncResultUseCase(syncResultRepository)

    val stopServer = StopServerUseCase(serverRepository)
}