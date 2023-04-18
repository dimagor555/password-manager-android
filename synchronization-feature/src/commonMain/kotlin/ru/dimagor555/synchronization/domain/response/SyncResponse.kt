package ru.dimagor555.synchronization.domain.response

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import ru.dimagor555.encryption.symmetric.domain.SymmetricEncryptor
import ru.dimagor555.encryption.symmetric.domain.SymmetricKey

@Serializable
sealed interface SyncResponse {

    @Serializable
    data class CommonSyncResponse(
        val passwordsAndFolderChildren: JsonObject,
        val requestingPasswordRecordIds: List<String>,
    ) : SyncResponse

    @Serializable
    data class SimpleRespondSyncResponse(
        val passwordsAndFolderChildren: JsonObject,
    ) : SyncResponse

    @Serializable
    data class SimpleRequestingSyncResponse(
        val requestingPasswordRecordIds: List<String>,
    ) : SyncResponse

    @Serializable
    object SuccessResponse : SyncResponse
}

internal suspend fun SyncResponse.encrypted(
    encryptor: SymmetricEncryptor,
    symmetricKey: SymmetricKey,
): EncryptedSyncResponse? =
    withContext(Dispatchers.Default) {
        val syncResponseJson = runCatching {
            json.encodeToString(this@encrypted)
        }.getOrNull() ?: return@withContext null
        val encryptedSyncResponseJson = encryptor.encrypt(syncResponseJson, symmetricKey)
        EncryptedSyncResponse(encryptedSyncResponseJson)
    }

private val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
}