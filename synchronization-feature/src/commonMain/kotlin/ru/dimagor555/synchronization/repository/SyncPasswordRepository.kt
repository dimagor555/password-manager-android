package ru.dimagor555.synchronization.repository

import ru.dimagor555.synchronization.domain.SyncPasswordRecord

interface SyncPasswordRepository {

    suspend fun getAllSyncRecords(): List<SyncPasswordRecord>

    suspend fun getAllSyncRecordsString(): String

    suspend fun getSyncRecordsStringByIds(ids: List<String>): String

    suspend fun getPasswordsStringByIds(ids: List<String>): String

    suspend fun addPasswords(value: String)

    suspend fun updatePasswords(value: String)
}
