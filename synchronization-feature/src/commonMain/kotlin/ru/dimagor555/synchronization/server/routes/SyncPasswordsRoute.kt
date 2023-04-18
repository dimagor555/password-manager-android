package ru.dimagor555.synchronization.server.routes

import io.github.aakira.napier.Napier
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get
import ru.dimagor555.synchronization.domain.passwordrecord.SyncPasswordRecord
import ru.dimagor555.synchronization.domain.request.EncryptedInitialSyncRequest
import ru.dimagor555.synchronization.domain.response.EncryptedSyncResponse
import ru.dimagor555.synchronization.domain.response.SyncResponse
import ru.dimagor555.synchronization.usecase.ServerSyncUseCase
import ru.dimagor555.synchronization.usecase.ServerSyncUseCase.ReceiveType
import ru.dimagor555.synchronization.usecase.ServerSyncUseCase.RespondType

fun Route.syncPasswords() {
    val serverSyncUseCase = get<ServerSyncUseCase>()

    route(SyncPasswordRecord.path) {
        post {
            Napier.e("Route.syncPasswords()")
            val encryptedInitialSyncRequest = call.receive<EncryptedInitialSyncRequest>()
            Napier.e("Route.syncPasswords() encryptedInitialSyncRequest = $encryptedInitialSyncRequest")
            serverSyncUseCase.receiveType.send(
                ReceiveType.InitialSyncRequestReceiveType(encryptedInitialSyncRequest)
            )
            val respondType = serverSyncUseCase.respondType.receive()
            Napier.e("Route.syncPasswords() respondType = $respondType")
            if (respondType is RespondType.SyncResponseRespondType) {
                call.respond(respondType.syncResponse as EncryptedSyncResponse)
            }
        }
        post("syncPasswords") {

            Napier.e("Route.syncPasswords() syncPasswords")
            val encryptedSyncResponse = call.receive<EncryptedSyncResponse>()
            Napier.e("Route.syncPasswords() syncPasswords encryptedSyncResponse = $encryptedSyncResponse")
            serverSyncUseCase.receiveType.send(
                ReceiveType.SyncResponseReceiveType(encryptedSyncResponse)
            )
            val respondType = serverSyncUseCase.respondType.receive()
            Napier.e("Route.syncPasswords() syncPasswords respondType = $respondType")
            if (respondType is RespondType.SuccessRespondType) {
                call.respond(SyncResponse.SuccessResponse)
            } else {
                call.respond(HttpStatusCode.OK)
            }
        }
    }
}
