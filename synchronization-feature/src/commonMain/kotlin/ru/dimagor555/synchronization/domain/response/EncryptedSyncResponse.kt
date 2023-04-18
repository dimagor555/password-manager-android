package ru.dimagor555.synchronization.domain.response

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.dimagor555.encryption.symmetric.domain.SymmetricDecryptor
import ru.dimagor555.encryption.symmetric.domain.SymmetricKey

@Serializable
@JvmInline
internal value class EncryptedSyncResponse(
    val encryptedString: String,
)

internal suspend fun EncryptedSyncResponse.decrypted(
    decryptor: SymmetricDecryptor,
    symmetricKey: SymmetricKey,
): SyncResponse? =
    withContext(Dispatchers.Default) {
        val commonSyncResponseJson = decryptor.decrypt(encryptedString, symmetricKey)
        val syncResponse = runCatching {
            json.decodeFromString<SyncResponse>(commonSyncResponseJson)
        }.getOrNull()
        syncResponse
    }

private val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
}