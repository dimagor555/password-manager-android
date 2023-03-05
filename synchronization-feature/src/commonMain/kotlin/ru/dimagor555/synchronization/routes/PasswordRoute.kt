package ru.dimagor555.synchronization.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.dimagor555.synchronization.domain.SyncPasswordRecord
import ru.dimagor555.synchronization.domain.SyncPasswords
import ru.dimagor555.synchronization.repository.ReceivePasswordApi

fun Route.syncPasswordRecords(receivePasswordApi: ReceivePasswordApi) {
    route(SyncPasswordRecord.path) {
        post {
            val syncPasswordRecords = call.receive<List<SyncPasswordRecord>>()
            val syncPasswords = receivePasswordApi.getSyncPasswords(syncPasswordRecords)
            call.respond(HttpStatusCode.OK, syncPasswords)
        }
        post("syncPasswords") {
            val requestPasswords = call.receive<SyncPasswords>()
            receivePasswordApi.addPasswords(requestPasswords)
            call.respond(HttpStatusCode.OK)
        }
    }
}
