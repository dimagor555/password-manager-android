package ru.dimagor555.synchronization.ui.deviceslistscreen.model

import ru.dimagor555.synchronization.data.ConnectionAddress
import ru.dimagor555.synchronization.data.SendPasswordApiImpl
import ru.dimagor555.synchronization.repository.ClientRepository
import ru.dimagor555.synchronization.repository.ServerRepository
import ru.dimagor555.synchronization.repository.SyncPasswordRepository
import ru.dimagor555.synchronization.usecase.FindSyncDevicesUseCase
import ru.dimagor555.synchronization.usecase.PostSyncPasswordRecordUseCase
import ru.dimagor555.synchronization.usecase.StartServerUseCase
import ru.dimagor555.synchronization.usecase.StopServerUseCase

class SyncUseCases(
    syncPasswordRepository: SyncPasswordRepository,
    clientRepository: ClientRepository,
    connectionAddress: ConnectionAddress,
    sendPasswordApiImpl: SendPasswordApiImpl,
    serverRepository: ServerRepository,
) {
    val findSyncDevicesUseCase = FindSyncDevicesUseCase(clientRepository)

    //возможно стоит вырезать connectionAddress, и просто передавать ip в метод post из syncDevice
    val postSyncPasswordRecordUseCase = PostSyncPasswordRecordUseCase(connectionAddress, sendPasswordApiImpl)

    val startServerUseCase = StartServerUseCase(serverRepository)
    val stopServerUseCase = StopServerUseCase(serverRepository)
}