package ru.dimagor555.synchronization.usecase.syncpassword

import ru.dimagor555.synchronization.domain.request.InitialSyncRequest
import ru.dimagor555.synchronization.domain.response.SyncResponse
import ru.dimagor555.synchronization.usecase.rest.repository.ReceivePasswordRepository

class GetSyncResponseUseCase(
    private val receivePasswordRepository: ReceivePasswordRepository,
) {
    suspend operator fun invoke(initialSyncRequest: InitialSyncRequest): SyncResponse =
        receivePasswordRepository.getSyncResponse(initialSyncRequest)
}