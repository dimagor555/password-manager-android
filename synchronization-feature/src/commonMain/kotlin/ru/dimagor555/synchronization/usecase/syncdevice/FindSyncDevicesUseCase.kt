package ru.dimagor555.synchronization.usecase.syncdevice

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dimagor555.synchronization.domain.syncdevice.SyncDevice
import ru.dimagor555.synchronization.usecase.syncdevice.api.SyncDeviceRepository

class FindSyncDevicesUseCase(
    private val syncDeviceRepository: SyncDeviceRepository,
) {
    suspend operator fun invoke(): List<SyncDevice> = withContext(Dispatchers.IO) {
        syncDeviceRepository.findSyncDevices()
    }
}
