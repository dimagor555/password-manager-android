package ru.dimagor555.synchronization.usecase.rest.api

import ru.dimagor555.synchronization.domain.key.EncodedAsymmetricPublicKey
import ru.dimagor555.synchronization.domain.key.EncryptedSymmetricKey
import ru.dimagor555.synchronization.domain.request.EncryptedInitialSyncRequest
import ru.dimagor555.synchronization.domain.response.EncryptedSyncResponse
import ru.dimagor555.synchronization.domain.response.SyncResponse

internal interface SendPasswordApi {

    suspend fun sendPublicAsymmetricKey(
        encodedAsymmetricPublicKey: EncodedAsymmetricPublicKey,
    ): EncryptedSymmetricKey?

    suspend fun sendSyncPasswordRecord(
        initialSyncRequest: EncryptedInitialSyncRequest,
    ): EncryptedSyncResponse?

    suspend fun sendRequestPasswords(
        encryptedSyncResponse: EncryptedSyncResponse,
    ): SyncResponse.SuccessResponse?

    suspend fun sendSuccessSyncResult()
}
