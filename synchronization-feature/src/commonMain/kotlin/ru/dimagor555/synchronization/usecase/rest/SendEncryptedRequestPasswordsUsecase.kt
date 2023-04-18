package ru.dimagor555.synchronization.usecase.rest

import ru.dimagor555.encryption.symmetric.domain.SymmetricDecryptor
import ru.dimagor555.encryption.symmetric.domain.SymmetricEncryptor
import ru.dimagor555.encryption.symmetric.domain.SymmetricKey
import ru.dimagor555.synchronization.domain.response.SyncResponse
import ru.dimagor555.synchronization.domain.response.SyncResponse.SimpleRespondSyncResponse
import ru.dimagor555.synchronization.domain.response.encrypted
import ru.dimagor555.synchronization.usecase.rest.repository.SendPasswordRepository
import ru.dimagor555.synchronization.usecase.syncpassword.repository.SyncPasswordRepository

internal class SendEncryptedRequestPasswordsUsecase(
    private val syncPasswordRepository: SyncPasswordRepository,
    private val encryptor: SymmetricEncryptor,
    private val sendPasswordRepository: SendPasswordRepository,
    private val decryptor: SymmetricDecryptor,
) {

    suspend operator fun invoke(
        requestingPasswordRecordIds: List<String>,
        symmetricKey: SymmetricKey,
    ): SyncResponse? {
        val respondPasswordsAndFolderChildren =
            syncPasswordRepository.getPasswordsAndFolderChildren(requestingPasswordRecordIds)
                ?: return null

        val simpleRespondSyncResponse = SimpleRespondSyncResponse(respondPasswordsAndFolderChildren)
        val encryptedSyncResponse =
            simpleRespondSyncResponse.encrypted(encryptor, symmetricKey) ?: return null

//        val encryptedResultSyncResponse =
//            sendPasswordRepository.sendRequestPasswords(encryptedSyncResponse)
//                ?: return null
//        val decryptedSyncResponse = encryptedResultSyncResponse.decrypted(decryptor, symmetricKey)
//        return decryptedSyncResponse

        val successSyncResponse = sendPasswordRepository.sendRequestPasswords(encryptedSyncResponse)
                ?: return null
        return successSyncResponse
    }
}