package ru.dimagor555.synchronization.server.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.dimagor555.synchronization.server.routes.authentication
import ru.dimagor555.synchronization.server.routes.syncPasswords
import ru.dimagor555.synchronization.util.getDeviceName

fun Application.configureRouting() {
    routing {
        authentication()
        syncPasswords()
        get("syncDevice") {
            call.respond(getDeviceName())
        }
    }
}
