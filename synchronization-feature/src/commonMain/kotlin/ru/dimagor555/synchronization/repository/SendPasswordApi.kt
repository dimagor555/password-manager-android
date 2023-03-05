package ru.dimagor555.synchronization.repository

import io.ktor.http.*
import ru.dimagor555.synchronization.domain.SyncPasswords

interface SendPasswordApi {

    suspend fun postSyncPasswordRecord(): SyncPasswords

    suspend fun addPasswords(syncPasswords: SyncPasswords)

    suspend fun postRequestPasswords(syncPasswords: SyncPasswords): HttpStatusCode
}
