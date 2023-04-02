package ru.dimagor555.synchronization.usecase.syncpassword

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.JsonObject
import ru.dimagor555.synchronization.domain.response.SyncResponse
import ru.dimagor555.synchronization.domain.syncstatus.SyncStatus
import ru.dimagor555.synchronization.usecase.rest.repository.SendPasswordRepository
import ru.dimagor555.synchronization.usecase.syncpassword.repository.SyncPasswordRepository
import ru.dimagor555.synchronization.usecase.syncstatus.SetSyncStatusUseCase

class PostSyncPasswordRecordUseCase(
    private val sendPasswordRepository: SendPasswordRepository,
    private val syncPasswordRepository: SyncPasswordRepository,
    private val setSyncStatus: SetSyncStatusUseCase,
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        when (val syncRespond = sendPasswordRepository.sendSyncPasswordRecord()) {
            is SyncResponse.CommonSyncResponse -> {
                addOrUpdateReceivedPasswords(syncRespond.passwordsAndFolderChildren)
                sendRequestPasswords(syncRespond.requestingPasswordRecordIds)
            }
            is SyncResponse.SimpleRespondSyncResponse -> {
                addOrUpdateReceivedPasswords(syncRespond.passwordsAndFolderChildren)
                sendSuccessSyncResult()
            }
            is SyncResponse.SimpleRequestingSyncResponse -> {
                sendRequestPasswords(syncRespond.requestingPasswordRecordIds)
            }
            is SyncResponse.SuccessResponse -> {
                setSyncStatus(SyncStatus.SuccessSync)
            }
            null -> {
                setSyncStatus(SyncStatus.ErrorSync)
            }
        }
    }

    private suspend fun addOrUpdateReceivedPasswords(passwordsAndFolderChildren: JsonObject) {
        setSyncStatus(SyncStatus.SavingReceivedPasswords)
        syncPasswordRepository.addOrUpdatePasswordsAndFolderChildren(passwordsAndFolderChildren)
    }

    private suspend fun sendRequestPasswords(requestingPasswordRecordIds: List<String>) {
        setSyncStatus(SyncStatus.SendingPasswords)
        sendPasswordRepository.sendRequestPasswords(requestingPasswordRecordIds)
    }

    private suspend fun sendSuccessSyncResult() {
        setSyncStatus(SyncStatus.SuccessSync)
        sendPasswordRepository.sendSuccessSyncResult()
    }
}
