package ru.dimagor555.synchronization.server.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get
import ru.dimagor555.synchronization.domain.passwordrecord.SyncPasswordRecord
import ru.dimagor555.synchronization.domain.request.InitialSyncRequest
import ru.dimagor555.synchronization.domain.response.SyncResponse
import ru.dimagor555.synchronization.domain.syncstatus.SyncStatus
import ru.dimagor555.synchronization.usecase.syncpassword.AddOrUpdatePasswordsAndFolderChildrenUseCase
import ru.dimagor555.synchronization.usecase.syncpassword.GetSyncResponseUseCase
import ru.dimagor555.synchronization.usecase.syncstatus.SetSyncStatusUseCase

fun Route.syncPasswordRecords() {
    val getSyncResponse = get<GetSyncResponseUseCase>()
    val addOrUpdatePasswordsAndFolderChildren = get<AddOrUpdatePasswordsAndFolderChildrenUseCase>()
    val setSyncStatus = get<SetSyncStatusUseCase>()

    route(SyncPasswordRecord.path) {
        post {
            val initialSyncRequest = call.receive<InitialSyncRequest>()
            setSyncStatus(SyncStatus.ReceivedPasswordsAnalysis)
            val syncResponse: SyncResponse = getSyncResponse(initialSyncRequest)
            when (syncResponse) {
                is SyncResponse.SuccessResponse -> setSyncStatus(SyncStatus.SuccessSync)
                else -> setSyncStatus(SyncStatus.SendingPasswords)
            }
            call.respond(syncResponse as SyncResponse)
        }
        post("syncPasswords") {
            when(val syncResponse = call.receive<SyncResponse>()) {
                is SyncResponse.SimpleRespondSyncResponse -> {
                    setSyncStatus(SyncStatus.SavingReceivedPasswords)
                    addOrUpdatePasswordsAndFolderChildren(syncResponse.passwordsAndFolderChildren)
                    setSyncStatus(SyncStatus.SuccessSync)
                    call.respond(SyncResponse.SuccessResponse as SyncResponse)
                }
                is SyncResponse.SuccessResponse -> {
                    setSyncStatus(SyncStatus.SuccessSync)
                    call.respond(HttpStatusCode.OK)
                }
                else -> error("Illegal SyncResponse")
            }
        }
    }
}
