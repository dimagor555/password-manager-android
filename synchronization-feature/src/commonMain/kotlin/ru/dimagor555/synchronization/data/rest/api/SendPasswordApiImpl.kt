package ru.dimagor555.synchronization.data.rest.api

import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.JsonObject
import ru.dimagor555.synchronization.data.connectionaddress.model.ConnectionAddress
import ru.dimagor555.synchronization.domain.passwordrecord.SyncPasswordRecord
import ru.dimagor555.synchronization.domain.request.InitialSyncRequest
import ru.dimagor555.synchronization.domain.response.SyncResponse
import ru.dimagor555.synchronization.domain.response.SyncResponse.SimpleRespondSyncResponse
import ru.dimagor555.synchronization.domain.response.SyncResponse.SuccessResponse
import ru.dimagor555.synchronization.usecase.rest.api.SendPasswordApi
import ru.dimagor555.synchronization.usecase.rest.repository.ClientRepository

class SendPasswordApiImpl(
    private val clientRepository: ClientRepository,
    private val connectionAddress: ConnectionAddress,
) : SendPasswordApi {

    private val client: HttpClient
        get() = clientRepository.client

    override suspend fun sendSyncPasswordRecord(passwords: List<SyncPasswordRecord>): SyncResponse? {
        val syncResponse = runCatching {
            client.post("http://${connectionAddress.ip}:8995/${SyncPasswordRecord.path}") {
                contentType(ContentType.Application.Json)
                setBody(InitialSyncRequest(passwords))
            }.body<SyncResponse>()
        }.onFailure {
            Napier.e("Error in SendPasswordApiImpl postSyncPasswordRecord: $it")
        }.getOrNull() ?: return null
        return syncResponse
    }

    override suspend fun sendRequestPasswords(respondPasswordsAndFolderChildren: JsonObject): SyncResponse? {
        val syncResponse = runCatching {
            client.post("http://${connectionAddress.ip}:8995/${SyncPasswordRecord.path}/syncPasswords") {
                contentType(ContentType.Application.Json)
                setBody(SimpleRespondSyncResponse(respondPasswordsAndFolderChildren) as SyncResponse)
            }.body<SyncResponse>()
        }.onFailure {
            Napier.e("Error in SendPasswordApiImpl postRequestPasswords: $it")
        }.getOrNull() ?: return null
        return syncResponse
    }

    override suspend fun sendSuccessSyncResult() {
        runCatching {
            client.post("http://${connectionAddress.ip}:8995/${SyncPasswordRecord.path}/syncPasswords") {
                contentType(ContentType.Application.Json)
                setBody(SuccessResponse as SyncResponse)
            }
        }.onFailure {
            Napier.e("Error in SendPasswordApiImpl postSuccessSyncResult: $it")
        }
    }
}
