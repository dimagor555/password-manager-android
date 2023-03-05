package ru.dimagor555.synchronization.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject
import ru.dimagor555.synchronization.data.ReceivePasswordApiImpl
import ru.dimagor555.synchronization.repository.ReceivePasswordApi
import ru.dimagor555.synchronization.routes.syncPasswordRecords

fun Application.configureRouting() {
    routing {
        val receivePasswordApi: ReceivePasswordApi by inject(ReceivePasswordApiImpl::class.java)
        syncPasswordRecords(receivePasswordApi)

        get("/") {
            call.respond(HttpStatusCode.OK)
        }
    }
}
