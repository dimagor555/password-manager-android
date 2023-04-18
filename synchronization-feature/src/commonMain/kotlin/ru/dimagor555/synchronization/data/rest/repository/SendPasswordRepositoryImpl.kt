package ru.dimagor555.synchronization.data.rest.repository

import ru.dimagor555.synchronization.domain.key.EncodedAsymmetricPublicKey
import ru.dimagor555.synchronization.domain.key.EncryptedSymmetricKey
import ru.dimagor555.synchronization.domain.request.EncryptedInitialSyncRequest
import ru.dimagor555.synchronization.domain.response.EncryptedSyncResponse
import ru.dimagor555.synchronization.domain.response.SyncResponse
import ru.dimagor555.synchronization.usecase.rest.api.SendPasswordApi
import ru.dimagor555.synchronization.usecase.rest.repository.SendPasswordRepository

internal class SendPasswordRepositoryImpl(
    private val sendPasswordApi: SendPasswordApi,
) : SendPasswordRepository {

    override suspend fun sendPublicAsymmetricKey(
        encodedAsymmetricPublicKey: EncodedAsymmetricPublicKey,
    ): EncryptedSymmetricKey? {
        return sendPasswordApi.sendPublicAsymmetricKey(encodedAsymmetricPublicKey)
    }

    override suspend fun sendSyncPasswordRecords(
        encryptedInitialSyncRequest: EncryptedInitialSyncRequest,
    ): EncryptedSyncResponse? =
        sendPasswordApi.sendSyncPasswordRecord(encryptedInitialSyncRequest)

    override suspend fun sendRequestPasswords(
        encryptedSyncResponse: EncryptedSyncResponse,
    ): SyncResponse.SuccessResponse? =
        sendPasswordApi.sendRequestPasswords(encryptedSyncResponse)

    override suspend fun sendSuccessSyncResult() {
        sendPasswordApi.sendSuccessSyncResult()
    }
}