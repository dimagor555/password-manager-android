package ru.dimagor555.export.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import ru.dimagor555.encryption.symmetric.domain.SymmetricDecryptor
import ru.dimagor555.encryption.symmetric.domain.SymmetricEncryptor

@Serializable
data class Export(
    val version: ExportVersion = ExportVersion.LATEST,
    val exportInstant: Instant = Clock.System.now(),
    val passwordsAndFolderChildren: JsonObject,
)

internal suspend fun Export.encrypted(encryptor: SymmetricEncryptor): EncryptedExport =
    withContext(Dispatchers.Default) {
        val export = this@encrypted
        val exportJson = json.encodeToString(export)
        val encryptedExportJson = encryptor.encrypt(exportJson)
        EncryptedExport(encryptedExportJson)
    }

internal suspend fun EncryptedExport.decrypted(decryptor: SymmetricDecryptor): Export =
    withContext(Dispatchers.Default) {
        val encryptedExport = this@decrypted
        val exportJson = decryptor.decrypt(encryptedExport.encryptedString)
        json.decodeFromString(exportJson)
    }

private val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
}