package ru.dimagor555.synchronization.domain.request

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.dimagor555.encryption.symmetric.domain.SymmetricDecryptor
import ru.dimagor555.encryption.symmetric.domain.SymmetricEncryptor
import ru.dimagor555.encryption.symmetric.domain.SymmetricKey
import ru.dimagor555.synchronization.domain.passwordrecord.SyncPasswordRecord

@Serializable
internal data class InitialSyncRequest(
    val passwordRecords: List<SyncPasswordRecord> = emptyList(),
)

internal suspend fun InitialSyncRequest.encrypted(
    encryptor: SymmetricEncryptor,
    symmetricKey: SymmetricKey,
): EncryptedInitialSyncRequest =
    withContext(Dispatchers.Default) {
        val initialSyncRequest = this@encrypted
        val initialSyncRequestJson = json.encodeToString(initialSyncRequest)
        val encryptedInitialSyncRequestJson =
            encryptor.encrypt(initialSyncRequestJson, symmetricKey)
        EncryptedInitialSyncRequest(encryptedInitialSyncRequestJson)
    }

internal suspend fun EncryptedInitialSyncRequest.decrypted(
    decryptor: SymmetricDecryptor,
    symmetricKey: SymmetricKey,
): InitialSyncRequest =
    withContext(Dispatchers.Default) {
        val encryptedInitialSyncRequest = this@decrypted
        val initialSyncRequestJson =
            decryptor.decrypt(encryptedInitialSyncRequest.encryptedString, symmetricKey)
        json.decodeFromString(initialSyncRequestJson)
    }

@Serializable
@JvmInline
internal value class EncryptedInitialSyncRequest(
    val encryptedString: String = "",
)

private val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
}
