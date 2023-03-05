package ru.dimagor555.synchronization.repository

import io.ktor.client.*
import ru.dimagor555.synchronization.domain.SyncDevice

interface ClientRepository {
    fun createClient(): HttpClient

    fun closeClient()

    suspend fun findSyncDevices(): List<SyncDevice>
}
