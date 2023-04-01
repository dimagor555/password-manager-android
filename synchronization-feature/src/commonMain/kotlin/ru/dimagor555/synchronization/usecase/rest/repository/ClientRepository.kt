package ru.dimagor555.synchronization.usecase.rest.repository

import io.ktor.client.*
import ru.dimagor555.synchronization.domain.syncdevice.SyncDevice

interface ClientRepository {
    val client: HttpClient

    fun createClient(): HttpClient

    fun closeClient()

    suspend fun findSyncDevices(): List<SyncDevice>
}
