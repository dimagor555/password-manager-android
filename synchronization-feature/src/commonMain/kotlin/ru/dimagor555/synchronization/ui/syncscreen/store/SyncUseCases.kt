package ru.dimagor555.synchronization.ui.syncscreen.store

import ru.dimagor555.synchronization.usecase.rest.StartServerUseCase
import ru.dimagor555.synchronization.usecase.rest.StopServerUseCase
import ru.dimagor555.synchronization.usecase.rest.repository.SendPasswordRepository
import ru.dimagor555.synchronization.usecase.rest.repository.ServerRepository
import ru.dimagor555.synchronization.usecase.syncpassword.PostSyncPasswordRecordUseCase
import ru.dimagor555.synchronization.usecase.syncpassword.repository.SyncPasswordRepository
import ru.dimagor555.synchronization.usecase.syncresult.UpdateSyncResultUseCase
import ru.dimagor555.synchronization.usecase.syncstatus.ObserveSyncStatusUseCase
import ru.dimagor555.synchronization.usecase.syncstatus.SetSyncStatusUseCase
import ru.dimagor555.synchronization.usecase.syncstatus.repository.SyncStatusRepository

class SyncUseCases(
    serverRepository: ServerRepository,
    sendPasswordRepository: SendPasswordRepository,
    syncPasswordRepository: SyncPasswordRepository,
    syncStatusRepository: SyncStatusRepository,
    setSyncStatus: SetSyncStatusUseCase,
    updateSyncResult: UpdateSyncResultUseCase,
) {
    val postSyncPasswordRecord =
        PostSyncPasswordRecordUseCase(sendPasswordRepository, syncPasswordRepository, setSyncStatus, updateSyncResult)

    val startServer = StartServerUseCase(serverRepository)
    val stopServer = StopServerUseCase(serverRepository)

    val observeSyncStatus = ObserveSyncStatusUseCase(syncStatusRepository)
}