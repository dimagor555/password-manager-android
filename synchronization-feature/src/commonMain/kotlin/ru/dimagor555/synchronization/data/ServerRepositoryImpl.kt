package ru.dimagor555.synchronization.data

import io.ktor.server.cio.*
import io.ktor.server.engine.*
import ru.dimagor555.synchronization.plugins.module
import ru.dimagor555.synchronization.repository.ServerRepository
import java.net.DatagramSocket
import java.net.InetAddress

class ServerRepositoryImpl : ServerRepository {
    private var server: ApplicationEngine? = null
    override fun createServer() {
        server = embeddedServer(CIO, 8995, host = getLocalHostAddress()) {
            module()
        }.start(wait = true)
    }

    override fun stopServer() {
        server?.stop()
        server = null
    }

    private fun getLocalHostAddress(): String {
        val socket = DatagramSocket()
        socket.connect(InetAddress.getByName("8.8.8.8"), 12345)
        return socket.localAddress.hostAddress
    }
}