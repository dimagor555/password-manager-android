package ru.dimagor555.synchronization.ui.syncscreen.store

import ru.dimagor555.synchronization.data.connectionaddress.model.ConnectionAddress
import ru.dimagor555.synchronization.usecase.connectionaddress.ClearConnectionAddressUseCase
import ru.dimagor555.synchronization.usecase.rest.CloseClientUseCase
import ru.dimagor555.synchronization.usecase.rest.StopServerUseCase
import ru.dimagor555.synchronization.usecase.rest.repository.ClientRepository
import ru.dimagor555.synchronization.usecase.rest.repository.SendPasswordRepository
import ru.dimagor555.synchronization.usecase.rest.repository.ServerRepository
import ru.dimagor555.synchronization.usecase.syncpassword.PostSyncPasswordRecordUseCase
import ru.dimagor555.synchronization.usecase.syncpassword.repository.SyncPasswordRepository
import ru.dimagor555.synchronization.usecase.syncstatus.ObserveSyncStatusUseCase
import ru.dimagor555.synchronization.usecase.syncstatus.SetSyncStatusUseCase
import ru.dimagor555.synchronization.usecase.syncstatus.repository.SyncStatusRepository

class SyncUseCases(
    sendPasswordRepository: SendPasswordRepository,
    syncPasswordRepository: SyncPasswordRepository,
    syncStatusRepository: SyncStatusRepository,
    setSyncStatus: SetSyncStatusUseCase,
    serverRepository: ServerRepository,
    clientRepository: ClientRepository,
    connectionAddress: ConnectionAddress,
) {
    val postSyncPasswordRecord =
        PostSyncPasswordRecordUseCase(sendPasswordRepository, syncPasswordRepository, setSyncStatus)

    val observeSyncStatus = ObserveSyncStatusUseCase(syncStatusRepository)

    val stopServer = StopServerUseCase(serverRepository)
    val closeClient = CloseClientUseCase(clientRepository)

    val clearConnectionAddress = ClearConnectionAddressUseCase(connectionAddress)
}