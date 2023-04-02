package ru.dimagor555.synchronization.data.rest.repository

import ru.dimagor555.synchronization.domain.response.SyncResponse
import ru.dimagor555.synchronization.domain.syncstatus.SyncStatus
import ru.dimagor555.synchronization.usecase.rest.api.SendPasswordApi
import ru.dimagor555.synchronization.usecase.rest.repository.SendPasswordRepository
import ru.dimagor555.synchronization.usecase.syncpassword.repository.SyncPasswordRepository
import ru.dimagor555.synchronization.usecase.syncstatus.SetSyncStatusUseCase

class SendPasswordRepositoryImpl(
    private val sendPasswordApi: SendPasswordApi,
    private val syncPasswordRepository: SyncPasswordRepository,
    private val setSyncStatus: SetSyncStatusUseCase,
) : SendPasswordRepository {

    override suspend fun sendSyncPasswordRecord(): SyncResponse? {
        val passwordRecords = syncPasswordRepository.getAllSyncRecords()
        setSyncStatus(SyncStatus.SendingPasswords)
        return sendPasswordApi.sendSyncPasswordRecord(passwordRecords)
    }

    override suspend fun sendRequestPasswords(syncPasswordsIds: List<String>) {
        val respondPasswordsAndFolderChildren =
            syncPasswordRepository.getPasswordsAndFolderChildren(syncPasswordsIds) ?: return
        when (sendPasswordApi.sendRequestPasswords(respondPasswordsAndFolderChildren)) {
            is SyncResponse.SuccessResponse -> setSyncStatus(SyncStatus.SuccessSync)
            else -> setSyncStatus(SyncStatus.ErrorSync)
        }
    }

    override suspend fun sendSuccessSyncResult() {
        sendPasswordApi.sendSuccessSyncResult()
    }
}