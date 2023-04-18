package ru.dimagor555.synchronization.usecase.rest

import ru.dimagor555.encryption.symmetric.domain.SymmetricDecryptor
import ru.dimagor555.encryption.symmetric.domain.SymmetricEncryptor
import ru.dimagor555.encryption.symmetric.domain.SymmetricKey
import ru.dimagor555.synchronization.domain.request.InitialSyncRequest
import ru.dimagor555.synchronization.domain.request.encrypted
import ru.dimagor555.synchronization.domain.response.SyncResponse
import ru.dimagor555.synchronization.domain.response.decrypted
import ru.dimagor555.synchronization.usecase.rest.repository.SendPasswordRepository
import ru.dimagor555.synchronization.usecase.syncpassword.repository.SyncPasswordRepository

internal class SendEncryptedSyncPasswordRecordsUsecase(
    private val syncPasswordRepository: SyncPasswordRepository,
    private val encryptor: SymmetricEncryptor,
    private val sendPasswordRepository: SendPasswordRepository,
    private val decryptor: SymmetricDecryptor,
) {

    suspend operator fun invoke(symmetricKey: SymmetricKey): SyncResponse? {
        val passwordRecords = syncPasswordRepository.getAllSyncRecords()

        val initialSyncRequest = InitialSyncRequest(passwordRecords)
        val encryptedInitialSyncRequest = initialSyncRequest.encrypted(encryptor, symmetricKey)

        val encryptedSyncResponse =
            sendPasswordRepository.sendSyncPasswordRecords(encryptedInitialSyncRequest) ?: return null
        val decryptedSyncResponse = encryptedSyncResponse.decrypted(decryptor, symmetricKey)
        return decryptedSyncResponse
    }
}