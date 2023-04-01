package ru.dimagor555.synchronization.ui.deviceslistscreen.store

import ru.dimagor555.synchronization.data.connectionaddress.model.ConnectionAddress
import ru.dimagor555.synchronization.usecase.connectionaddress.SetConnectionAddressUseCase
import ru.dimagor555.synchronization.usecase.rest.StartServerUseCase
import ru.dimagor555.synchronization.usecase.rest.StopServerUseCase
import ru.dimagor555.synchronization.usecase.rest.repository.ClientRepository
import ru.dimagor555.synchronization.usecase.rest.repository.ServerRepository
import ru.dimagor555.synchronization.usecase.syncdevice.FindSyncDevicesUseCase
import ru.dimagor555.synchronization.usecase.syncstatus.ObserveSyncStatusUseCase
import ru.dimagor555.synchronization.usecase.syncstatus.repository.SyncStatusRepository

class DevicesListUseCases(
    clientRepository: ClientRepository,
    connectionAddress: ConnectionAddress,
    serverRepository: ServerRepository,
    syncStatusRepository: SyncStatusRepository,
) {
    val findSyncDevices = FindSyncDevicesUseCase(clientRepository)

    val setConnectionAddress = SetConnectionAddressUseCase(connectionAddress)

    val startServer = StartServerUseCase(serverRepository)
    val stopServer = StopServerUseCase(serverRepository)

    val observeSyncStatus = ObserveSyncStatusUseCase(syncStatusRepository)
}