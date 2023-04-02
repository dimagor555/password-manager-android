package ru.dimagor555.synchronization.data.syncdevice.api

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.*
import ru.dimagor555.synchronization.data.util.getLocalAddress
import ru.dimagor555.synchronization.domain.syncdevice.SyncDevice
import ru.dimagor555.synchronization.usecase.rest.repository.ClientRepository
import ru.dimagor555.synchronization.usecase.syncdevice.api.SyncDeviceApi

class SyncDeviceApiImpl(
    private val clientRepository: ClientRepository,
) : SyncDeviceApi {

    override suspend fun findSyncDevices(): List<SyncDevice> = withContext(Dispatchers.IO) {
        val responses = findAvailableConnections()
        val localAddress = getLocalAddress()
        val syncDeviceStates = responses
            .map { it.toSyncDevice() }
            .filter { it.address != localAddress }
        syncDeviceStates
    }

    private suspend fun findAvailableConnections(): List<HttpResponse> = coroutineScope {
        (2..254)
            .map { connectToDevicesInSubnetAsync(it) }
            .mapNotNull { it.await() }
            .filter { it.status.value in 200..299 }
    }

    private fun CoroutineScope.connectToDevicesInSubnetAsync(
        networkNumber: Int,
    ): Deferred<HttpResponse?> = async {
        runCatching {
            clientRepository.client.get {
                    url {
                        host = "192.168.${getSubnet()}.$networkNumber"
                        port = 8995
                        path("syncDevice")
                    }
                }
        }.getOrNull()
    }

    private fun getSubnet(): String {
        return getLocalAddress().split(".")[2]
    }

    private suspend fun HttpResponse.toSyncDevice() = SyncDevice(
        hostName = body(),
        address = request.url.host,
    )
}