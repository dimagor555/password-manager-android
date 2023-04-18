package ru.dimagor555.synchronization.usecase.rest

import ru.dimagor555.encryption.symmetric.domain.SymmetricDecryptor
import ru.dimagor555.encryption.symmetric.domain.SymmetricEncryptor
import ru.dimagor555.encryption.symmetric.domain.SymmetricKey
import ru.dimagor555.synchronization.domain.request.EncryptedInitialSyncRequest
import ru.dimagor555.synchronization.domain.request.decrypted
import ru.dimagor555.synchronization.domain.response.EncryptedSyncResponse
import ru.dimagor555.synchronization.domain.response.SyncResponse
import ru.dimagor555.synchronization.domain.response.encrypted
import ru.dimagor555.synchronization.usecase.syncpassword.GetSyncResponseUseCase

internal class ReceiveEncryptedInitialSyncRequestUsecase(
    private val decryptor: SymmetricDecryptor,
    private val getSyncResponse: GetSyncResponseUseCase,
    private val encryptor: SymmetricEncryptor,
) {

    suspend operator fun invoke(
        encryptedInitialSyncRequest: EncryptedInitialSyncRequest,
        symmetricKey: SymmetricKey,
    ): EncryptedSyncResponse? {
        val initialSyncRequest = encryptedInitialSyncRequest.decrypted(decryptor, symmetricKey)
        val syncResponse: SyncResponse = getSyncResponse(initialSyncRequest)
        val encryptedSyncResponse = syncResponse.encrypted(encryptor, symmetricKey)
        return encryptedSyncResponse
    }
}