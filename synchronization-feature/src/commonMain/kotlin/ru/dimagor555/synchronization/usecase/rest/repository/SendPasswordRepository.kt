package ru.dimagor555.synchronization.usecase.rest.repository

import ru.dimagor555.synchronization.domain.response.SyncResponse

interface SendPasswordRepository {

    suspend fun sendSyncPasswordRecord(): SyncResponse?

    suspend fun sendRequestPasswords(syncPasswordsIds: List<String>)

    suspend fun sendSuccessSyncResult()
}