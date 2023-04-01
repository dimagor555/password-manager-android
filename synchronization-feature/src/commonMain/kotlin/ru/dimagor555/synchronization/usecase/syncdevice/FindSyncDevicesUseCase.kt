package ru.dimagor555.synchronization.usecase.syncdevice

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dimagor555.synchronization.domain.syncdevice.SyncDevice
import ru.dimagor555.synchronization.usecase.rest.repository.ClientRepository

class FindSyncDevicesUseCase(
    private val clientRepository: ClientRepository,
) {
    suspend operator fun invoke(): List<SyncDevice> = withContext(Dispatchers.IO) {
        clientRepository.findSyncDevices()
    }
}
