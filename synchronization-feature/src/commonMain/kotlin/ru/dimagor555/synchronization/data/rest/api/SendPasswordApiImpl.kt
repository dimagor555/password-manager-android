package ru.dimagor555.synchronization.data.rest.api

import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import ru.dimagor555.synchronization.data.connectionaddress.model.ConnectionAddress
import ru.dimagor555.synchronization.domain.key.EncodedAsymmetricPublicKey
import ru.dimagor555.synchronization.domain.key.EncryptedSymmetricKey
import ru.dimagor555.synchronization.domain.passwordrecord.SyncPasswordRecord
import ru.dimagor555.synchronization.domain.request.EncryptedInitialSyncRequest
import ru.dimagor555.synchronization.domain.response.EncryptedSyncResponse
import ru.dimagor555.synchronization.domain.response.SyncResponse
import ru.dimagor555.synchronization.usecase.rest.api.SendPasswordApi
import ru.dimagor555.synchronization.usecase.rest.repository.ClientRepository

internal class SendPasswordApiImpl(
    private val clientRepository: ClientRepository,
    private val connectionAddress: ConnectionAddress,
) : SendPasswordApi {

    private val client: HttpClient
        get() = clientRepository.client

    override suspend fun sendPublicAsymmetricKey(
        encodedAsymmetricPublicKey: EncodedAsymmetricPublicKey,
    ): EncryptedSymmetricKey? {
        val syncResponse = runCatching {
            client.post("http://${connectionAddress.ip}:8995/authentication") {
                contentType(ContentType.Application.Json)
                setBody(encodedAsymmetricPublicKey)
            }.body<EncryptedSymmetricKey>()
        }.onFailure {
            Napier.e("Error in SendPasswordApiImpl sendPublicAsymmetricKey: $it")
        }.getOrNull() ?: return null
        return syncResponse
    }

    override suspend fun sendSyncPasswordRecord(
        initialSyncRequest: EncryptedInitialSyncRequest,
    ): EncryptedSyncResponse? {
        val syncResponse = runCatching {
            client.post("http://${connectionAddress.ip}:8995/${SyncPasswordRecord.path}") {
                contentType(ContentType.Application.Json)
                setBody(initialSyncRequest)
            }.body<EncryptedSyncResponse>()
        }.onFailure {
            Napier.e("Error in SendPasswordApiImpl postSyncPasswordRecord: $it")
        }.getOrNull() ?: return null
        return syncResponse
    }

    override suspend fun sendRequestPasswords(
        encryptedSyncResponse: EncryptedSyncResponse,
    ): SyncResponse.SuccessResponse? {
        val syncResponse = runCatching {
            client.post("http://${connectionAddress.ip}:8995/${SyncPasswordRecord.path}/syncPasswords") {
                contentType(ContentType.Application.Json)
                setBody(encryptedSyncResponse)
            }.body<SyncResponse.SuccessResponse>()
        }.onFailure {
            Napier.e("Error in SendPasswordApiImpl postRequestPasswords: $it")
        }.getOrNull() ?: return null
        return syncResponse
    }

    override suspend fun sendSuccessSyncResult() {
        runCatching {
            client.post("http://${connectionAddress.ip}:8995/${SyncPasswordRecord.path}/syncPasswords") {
                contentType(ContentType.Application.Json)
                setBody(SyncResponse.SuccessResponse as SyncResponse)
            }
        }.onFailure {
            Napier.e("Error in SendPasswordApiImpl postSuccessSyncResult: $it")
        }
    }
}
