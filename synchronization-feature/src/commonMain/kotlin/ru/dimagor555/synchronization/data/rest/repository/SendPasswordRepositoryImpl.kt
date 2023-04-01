package ru.dimagor555.synchronization.data.rest.repository

import io.github.aakira.napier.Napier
import ru.dimagor555.synchronization.domain.response.SyncResponse
import ru.dimagor555.synchronization.domain.syncstatus.SyncStatus
import ru.dimagor555.synchronization.usecase.rest.api.SendPasswordApi
import ru.dimagor555.synchronization.usecase.rest.repository.SendPasswordRepository
import ru.dimagor555.synchronization.usecase.syncpassword.repository.SyncPasswordRepository
import ru.dimagor555.synchronization.usecase.syncresult.UpdateSyncResultUseCase
import ru.dimagor555.synchronization.usecase.syncstatus.SetSyncStatusUseCase

class SendPasswordRepositoryImpl(
    private val sendPasswordApi: SendPasswordApi,
    private val syncPasswordRepository: SyncPasswordRepository,
    private val setSyncStatus: SetSyncStatusUseCase,
    private val updateSyncResult: UpdateSyncResultUseCase,
) : SendPasswordRepository {

    override suspend fun postSyncPasswordRecord(): SyncResponse? {
        val passwordRecords = syncPasswordRepository.getAllSyncRecords()
        setSyncStatus(SyncStatus.SendingPasswords)
        return sendPasswordApi.postSyncPasswordRecord(passwordRecords)
    }

    override suspend fun postRequestPasswords(syncPasswordsIds: List<String>) {
        Napier.e("SendPasswordRepositoryImpl postRequestPasswords syncPasswordsIds = $syncPasswordsIds")
        val respondPasswordsAndFolderChildren =
            syncPasswordRepository.getPasswordsAndFolderChildren(syncPasswordsIds) ?: return
        Napier.e("SendPasswordRepositoryImpl postRequestPasswords respondPasswordsAndFolderChildren = $respondPasswordsAndFolderChildren")
        when (sendPasswordApi.postRequestPasswords(respondPasswordsAndFolderChildren)) {
            is SyncResponse.SuccessResponse -> setSyncStatus(SyncStatus.SuccessSync)
            else -> setSyncStatus(SyncStatus.ErrorSync)
        }
    }

    override suspend fun postSuccessSyncResult() {
        sendPasswordApi.postSuccessSyncResult()
    }
}