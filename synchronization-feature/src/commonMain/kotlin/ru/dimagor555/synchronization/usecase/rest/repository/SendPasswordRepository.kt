package ru.dimagor555.synchronization.usecase.rest.repository

import ru.dimagor555.synchronization.domain.response.SyncResponse

interface SendPasswordRepository {

    suspend fun postSyncPasswordRecord(): SyncResponse?

    suspend fun postRequestPasswords(syncPasswordsIds: List<String>)

    suspend fun postSuccessSyncResult()
}