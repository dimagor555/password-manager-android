package ru.dimagor555.synchronization.usecase.syncpassword

import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dimagor555.synchronization.domain.response.SyncResponse
import ru.dimagor555.synchronization.domain.syncstatus.SyncStatus
import ru.dimagor555.synchronization.usecase.rest.repository.SendPasswordRepository
import ru.dimagor555.synchronization.usecase.syncpassword.repository.SyncPasswordRepository
import ru.dimagor555.synchronization.usecase.syncresult.UpdateSyncResultUseCase
import ru.dimagor555.synchronization.usecase.syncstatus.SetSyncStatusUseCase

class PostSyncPasswordRecordUseCase(
    private val sendPasswordRepository: SendPasswordRepository,
    private val syncPasswordRepository: SyncPasswordRepository,
    private val setSyncStatus: SetSyncStatusUseCase,
    private val updateSyncResult: UpdateSyncResultUseCase,
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        when (val syncRespond = sendPasswordRepository.postSyncPasswordRecord()) {
            is SyncResponse.CommonSyncResponse -> {
                Napier.e("PostSyncPasswordRecordUseCase $syncRespond")
                setSyncStatus(SyncStatus.SavingReceivedPasswords)
                syncPasswordRepository.addOrUpdatePasswordsAndFolderChildren(syncRespond.passwordsAndFolderChildren)
                setSyncStatus(SyncStatus.SendingPasswords)
                sendPasswordRepository.postRequestPasswords(syncRespond.requestingPasswordRecordIds)
            }
            is SyncResponse.SimpleRespondSyncResponse -> {
                Napier.e("PostSyncPasswordRecordUseCase $syncRespond")
                setSyncStatus(SyncStatus.SavingReceivedPasswords)
                syncPasswordRepository.addOrUpdatePasswordsAndFolderChildren(syncRespond.passwordsAndFolderChildren)
                setSyncStatus(SyncStatus.SuccessSync)
                sendPasswordRepository.postSuccessSyncResult()
            }
            is SyncResponse.SimpleRequestingSyncResponse -> {
                Napier.e("PostSyncPasswordRecordUseCase $syncRespond")
                setSyncStatus(SyncStatus.SendingPasswords)
                sendPasswordRepository.postRequestPasswords(syncRespond.requestingPasswordRecordIds)
            }
            is SyncResponse.SuccessResponse -> {
                Napier.e("PostSyncPasswordRecordUseCase $syncRespond")
                setSyncStatus(SyncStatus.SuccessSync)
            }
            null -> {
                setSyncStatus(SyncStatus.ErrorSync)
            }
        }
    }
}
