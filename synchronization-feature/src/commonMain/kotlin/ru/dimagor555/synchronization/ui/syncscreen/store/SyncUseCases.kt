package ru.dimagor555.synchronization.ui.syncscreen.store

import ru.dimagor555.encryption.asymmetric.domain.AsymmetricDecryptor
import ru.dimagor555.synchronization.data.connectionaddress.model.ConnectionAddress
import ru.dimagor555.synchronization.usecase.ClientSyncUseCase
import ru.dimagor555.synchronization.usecase.connectionaddress.ClearConnectionAddressUseCase
import ru.dimagor555.synchronization.usecase.rest.CloseClientUseCase
import ru.dimagor555.synchronization.usecase.rest.SendEncryptedRequestPasswordsUsecase
import ru.dimagor555.synchronization.usecase.rest.SendEncryptedSyncPasswordRecordsUsecase
import ru.dimagor555.synchronization.usecase.rest.StopServerUseCase
import ru.dimagor555.synchronization.usecase.rest.repository.ClientRepository
import ru.dimagor555.synchronization.usecase.rest.repository.SendPasswordRepository
import ru.dimagor555.synchronization.usecase.rest.repository.ServerRepository
import ru.dimagor555.synchronization.usecase.syncpassword.repository.SyncPasswordRepository
import ru.dimagor555.synchronization.usecase.syncstatus.ObserveSyncStatusUseCase
import ru.dimagor555.synchronization.usecase.syncstatus.SetSyncStatusUseCase
import ru.dimagor555.synchronization.usecase.syncstatus.repository.SyncStatusRepository

internal class SyncUseCases(
    setSyncStatus: SetSyncStatusUseCase,
    asymmetricDecryptor: AsymmetricDecryptor,
    sendPasswordRepository: SendPasswordRepository,
    syncPasswordRepository: SyncPasswordRepository,
    sendEncryptedSyncPasswordRecordsUsecase: SendEncryptedSyncPasswordRecordsUsecase,
    sendEncryptedRequestPasswordsUsecase: SendEncryptedRequestPasswordsUsecase,
    syncStatusRepository: SyncStatusRepository,
    serverRepository: ServerRepository,
    clientRepository: ClientRepository,
    connectionAddress: ConnectionAddress,
) {
    val clientSync = ClientSyncUseCase(
        setSyncStatus,
        asymmetricDecryptor,
        sendPasswordRepository,
        syncPasswordRepository,
        sendEncryptedSyncPasswordRecordsUsecase,
        sendEncryptedRequestPasswordsUsecase,
    )

    val observeSyncStatus = ObserveSyncStatusUseCase(syncStatusRepository)
    val setSyncStatus = SetSyncStatusUseCase(syncStatusRepository)

    val stopServer = StopServerUseCase(serverRepository)
    val closeClient = CloseClientUseCase(clientRepository)

    val clearConnectionAddress = ClearConnectionAddressUseCase(connectionAddress)
}