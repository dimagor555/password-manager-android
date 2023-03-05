package ru.dimagor555.synchronization.data

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import ru.dimagor555.synchronization.domain.SyncDevice
import ru.dimagor555.synchronization.repository.ClientRepository
import java.net.DatagramSocket
import java.net.InetAddress

class ClientRepositoryImpl(
//    private val connectionAddress: ConnectionAddress,
) : ClientRepository {
    private var client: HttpClient? = null

    override fun createClient() = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
    }

    override fun closeClient() {
        client?.close()
    }

    override suspend fun findSyncDevices(): List<SyncDevice> = withContext(Dispatchers.IO) {
        val subnet = getSubnet()
        val client = client ?: createClient()
        val responses = (2..254).map {
            async { runCatching { client.request("http://192.168.$subnet.$it:8995") }.getOrNull() }
        }.mapNotNull {
            it.await()
        }.filter { it.status.value in 200..299 }

//        if (responses.isNotEmpty()) {
//            connectionAddress.ip = "http://${responses.first().request.url.host}:8995"
//        }

        val syncDevices = responses.map {
            SyncDevice(
                hostName = getDeviceName(),
                address = "http://${it.request.url.host}:8995",
            )
        }

        return@withContext syncDevices
    }

    private fun getSubnet(): String {
        val socket = DatagramSocket()
        socket.connect(InetAddress.getByName("8.8.8.8"), 12345)
        return socket.localAddress.hostAddress.split(".")[2]
    }

    private fun getDeviceName(): String {
        val localHost = InetAddress.getLocalHost()
        return localHost.hostName
    }
}