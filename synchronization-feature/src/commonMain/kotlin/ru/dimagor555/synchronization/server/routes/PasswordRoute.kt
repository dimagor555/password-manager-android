package ru.dimagor555.synchronization.server.routes

import io.github.aakira.napier.Napier
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
import ru.dimagor555.synchronization.usecase.syncresult.UpdateSyncResultUseCase
import ru.dimagor555.synchronization.usecase.syncstatus.SetSyncStatusUseCase

fun Route.syncPasswordRecords() {
    val getSyncResponse = get<GetSyncResponseUseCase>()
    val addOrUpdatePasswordsAndFolderChildren = get<AddOrUpdatePasswordsAndFolderChildrenUseCase>()
    val setSyncStatus = get<SetSyncStatusUseCase>()
    val updateSyncResult = get<UpdateSyncResultUseCase>()
    route(SyncPasswordRecord.path) {
        post {
            Napier.e("Route.syncPasswordRecords SyncPasswordRecord.path")
            val initialSyncRequest = call.receive<InitialSyncRequest>()
            setSyncStatus(SyncStatus.ReceivedPasswordsAnalysis)
            Napier.e("Route.syncPasswordRecords initialSyncRequest = $initialSyncRequest")
            val syncResponse: SyncResponse = getSyncResponse(initialSyncRequest)
            Napier.e("Route.syncPasswordRecords syncRespond = $syncResponse")
            when (syncResponse) {
                is SyncResponse.SuccessResponse -> setSyncStatus(SyncStatus.SuccessSync)
                else -> setSyncStatus(SyncStatus.SendingPasswords)
            }
            call.respond(syncResponse as SyncResponse)
        }
        post("syncPasswords") {
            Napier.e("Route.syncPasswordRecords syncPasswords 1")

            val syncResponse = call.receive<SyncResponse>()
            Napier.e("Route.syncPasswordRecords simpleSyncResponse 1 = $syncResponse")

            when(syncResponse) {
                is SyncResponse.SimpleRespondSyncResponse -> {
                    setSyncStatus(SyncStatus.SavingReceivedPasswords)
                    Napier.e("Route.syncPasswordRecords simpleSyncResponse = $syncResponse")
                    addOrUpdatePasswordsAndFolderChildren(syncResponse.passwordsAndFolderChildren)
                    Napier.e("Route.syncPasswordRecords syncPasswords 2")
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
