package ru.dimagor555.synchronization.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dimagor555.synchronization.domain.SyncDevice
import ru.dimagor555.synchronization.repository.ClientRepository

class FindSyncDevicesUseCase(
    private val clientRepository: ClientRepository,
) {
    suspend operator fun invoke(): List<SyncDevice> = withContext(Dispatchers.IO) {
        clientRepository.findSyncDevices()
    }
}
