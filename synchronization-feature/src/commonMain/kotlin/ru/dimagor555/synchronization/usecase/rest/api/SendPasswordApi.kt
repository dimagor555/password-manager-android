package ru.dimagor555.synchronization.usecase.rest.api

import kotlinx.serialization.json.JsonObject
import ru.dimagor555.synchronization.domain.passwordrecord.SyncPasswordRecord
import ru.dimagor555.synchronization.domain.response.SyncResponse

interface SendPasswordApi {

    suspend fun postSyncPasswordRecord(passwords: List<SyncPasswordRecord>): SyncResponse?

    suspend fun postRequestPasswords(respondPasswordsAndFolderChildren: JsonObject): SyncResponse?

    suspend fun postSuccessSyncResult()
}
