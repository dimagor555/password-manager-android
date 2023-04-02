package ru.dimagor555.synchronization.data.syncdevice.repository

import ru.dimagor555.synchronization.domain.syncdevice.SyncDevice
import ru.dimagor555.synchronization.usecase.syncdevice.api.SyncDeviceApi
import ru.dimagor555.synchronization.usecase.syncdevice.api.SyncDeviceRepository

class SyncDeviceRepositoryImpl(
    private val syncDeviceApi: SyncDeviceApi,
) : SyncDeviceRepository {

    override suspend fun findSyncDevices(): List<SyncDevice> =
        syncDeviceApi.findSyncDevices()
}