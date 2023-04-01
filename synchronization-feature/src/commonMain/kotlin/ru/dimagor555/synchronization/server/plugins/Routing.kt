package ru.dimagor555.synchronization.server.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.dimagor555.synchronization.server.routes.syncPasswordRecords
import ru.dimagor555.synchronization.util.getDeviceName

fun Application.configureRouting() {
    routing {
        syncPasswordRecords()

        //TODO do something about it
        get("syncDevice") {
            call.respond(getDeviceName())
        }
        get("/") {
            call.respond(HttpStatusCode.OK)
        }
    }
}
