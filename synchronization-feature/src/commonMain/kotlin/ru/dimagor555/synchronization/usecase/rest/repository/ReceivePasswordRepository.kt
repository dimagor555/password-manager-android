package ru.dimagor555.synchronization.usecase.rest.repository

import ru.dimagor555.synchronization.domain.request.InitialSyncRequest
import ru.dimagor555.synchronization.domain.response.SyncResponse

interface ReceivePasswordRepository {

    suspend fun getSyncResponse(initialSyncRequest: InitialSyncRequest): SyncResponse
}