package ru.dimagor555.synchronization.ui.resultsyncscreen.store

import ru.dimagor555.synchronization.data.connectionaddress.model.ConnectionAddress
import ru.dimagor555.synchronization.usecase.connectionaddress.ClearConnectionAddressUseCase
import ru.dimagor555.synchronization.usecase.rest.CloseClientUseCase
import ru.dimagor555.synchronization.usecase.rest.StopServerUseCase
import ru.dimagor555.synchronization.usecase.rest.repository.ClientRepository
import ru.dimagor555.synchronization.usecase.rest.repository.ServerRepository
import ru.dimagor555.synchronization.usecase.syncresult.GetSyncResultUseCase
import ru.dimagor555.synchronization.usecase.syncresult.repository.SyncResultRepository

class ResultSyncUseCases(
    syncResultRepository: SyncResultRepository,
    serverRepository: ServerRepository,
    clientRepository: ClientRepository,
    connectionAddress: ConnectionAddress,
) {
    val getSyncResult = GetSyncResultUseCase(syncResultRepository)

    val stopServer = StopServerUseCase(serverRepository)
    val closeClient = CloseClientUseCase(clientRepository)

    val clearConnectionAddress = ClearConnectionAddressUseCase(connectionAddress)
}