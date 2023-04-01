package ru.dimagor555.synchronization.data.rest.repository

import io.ktor.server.cio.*
import io.ktor.server.engine.*
import ru.dimagor555.synchronization.server.plugins.module
import ru.dimagor555.synchronization.usecase.rest.repository.ServerRepository
import java.net.DatagramSocket
import java.net.InetAddress

class ServerRepositoryImpl : ServerRepository {
    private val server: CIOApplicationEngine by lazy {
        embeddedServer(CIO, 8995, host = getLocalHostAddress()) {
            module()
        }
    }

    override fun createServer() {
        server.start(wait = true)
    }

    override fun stopServer() {
        server.stop()
    }

    private fun getLocalHostAddress(): String {
        val socket = DatagramSocket()
        socket.connect(InetAddress.getByName("8.8.8.8"), 12345)
        return socket.localAddress.hostAddress
    }
}