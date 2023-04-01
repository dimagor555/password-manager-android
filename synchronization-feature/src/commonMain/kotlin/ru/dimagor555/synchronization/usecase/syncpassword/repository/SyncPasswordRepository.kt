package ru.dimagor555.synchronization.usecase.syncpassword.repository

import kotlinx.serialization.json.JsonObject
import ru.dimagor555.synchronization.domain.passwordrecord.SyncPasswordRecord

interface SyncPasswordRepository {

    suspend fun getAllSyncRecords(): List<SyncPasswordRecord>

    suspend fun getPasswordsAndFolderChildren(passwordsIds: List<String>): JsonObject?

    suspend fun addOrUpdatePasswordsAndFolderChildren(passwordsAndFolderChildren: JsonObject)
}
