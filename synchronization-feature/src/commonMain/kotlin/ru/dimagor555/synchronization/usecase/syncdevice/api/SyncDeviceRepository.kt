package ru.dimagor555.synchronization.usecase.syncdevice.api

import ru.dimagor555.synchronization.domain.syncdevice.SyncDevice

interface SyncDeviceRepository {

    suspend fun findSyncDevices(): List<SyncDevice>
}