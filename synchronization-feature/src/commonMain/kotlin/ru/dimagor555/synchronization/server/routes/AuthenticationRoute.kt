package ru.dimagor555.synchronization.server.routes

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get
import ru.dimagor555.synchronization.domain.key.EncodedAsymmetricPublicKey
import ru.dimagor555.synchronization.usecase.ServerSyncUseCase
import ru.dimagor555.synchronization.usecase.ServerSyncUseCase.ReceiveType
import ru.dimagor555.synchronization.usecase.ServerSyncUseCase.RespondType

fun Route.authentication() {
    val serverSyncUseCase = get<ServerSyncUseCase>()

    route("authentication") {
        post {
            val asymmetricPublicKey = call.receive<EncodedAsymmetricPublicKey>()
            serverSyncUseCase
                .receiveType
                .send(ReceiveType.AsymmetricPublicKeyReceiveType(asymmetricPublicKey))

            val symmetricKey = serverSyncUseCase
                .respondType
                .receive() as? RespondType.SymmetricKeyRespondType ?: return@post
            call.respond(symmetricKey.encryptedSymmetricKey)
        }
    }
}
