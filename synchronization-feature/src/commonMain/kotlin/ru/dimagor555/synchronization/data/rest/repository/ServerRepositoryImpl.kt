package ru.dimagor555.synchronization.data.rest.repository

import io.ktor.server.cio.*
import io.ktor.server.engine.*
import ru.dimagor555.synchronization.data.util.getLocalAddress
import ru.dimagor555.synchronization.server.plugins.module
import ru.dimagor555.synchronization.usecase.rest.repository.ServerRepository

class ServerRepositoryImpl : ServerRepository {

    private var server: CIOApplicationEngine? = null

    override fun startServer() {
        if (server == null) {
            server = embeddedServer(CIO, 8995, host = getLocalAddress()) {
                module()
            }
            server!!.start(wait = true)
        }
    }

    override fun stopServer() {
        server?.stop(gracePeriodMillis = 200, timeoutMillis = 200)
        server = null
    }
}