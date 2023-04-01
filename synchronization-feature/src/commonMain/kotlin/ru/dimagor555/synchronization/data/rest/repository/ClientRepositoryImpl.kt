package ru.dimagor555.synchronization.data.rest.repository

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import ru.dimagor555.synchronization.domain.syncdevice.SyncDevice
import ru.dimagor555.synchronization.usecase.rest.repository.ClientRepository
import java.net.DatagramSocket
import java.net.InetAddress

class ClientRepositoryImpl : ClientRepository {

    override val client: HttpClient by lazy {
        createClient()
    }

    override fun createClient() = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }
    }

    override fun closeClient() {
        client.close()
    }

    override suspend fun findSyncDevices(): List<SyncDevice> = withContext(Dispatchers.IO) {
        val subnet = getSubnet()
        val localAddress = getLocalAddress()
        val responses = findAvailableConnections(subnet)
        val syncDeviceStates = responses
            .map {
                SyncDevice(
                    hostName = it.body(),
                    address = it.request.url.host,
                )
            }.filter {
                it.address != localAddress
            }
        syncDeviceStates
    }

    private suspend fun findAvailableConnections(subnet: String): List<HttpResponse> =
        withContext(Dispatchers.IO) {
            (2..254)
                .map {
                    async {
                        runCatching {
                            client.get("http://192.168.$subnet.$it:8995/syncDevice")
                        }.getOrNull()
                    }
                }.mapNotNull {
                    it.await()
                }.filter {
                    it.status.value in 200..299
                }
        }

    private fun getSubnet(): String {
        return getLocalAddress().split(".")[2]
    }

    private fun getLocalAddress(): String {
        val socket = DatagramSocket()
        socket.connect(InetAddress.getByName("8.8.8.8"), 12345)
        return socket.localAddress.hostAddress
    }
}