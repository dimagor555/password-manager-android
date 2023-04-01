package ru.dimagor555.synchronization.data.rest.repository

import io.github.aakira.napier.Napier
import kotlinx.serialization.json.JsonObject
import ru.dimagor555.synchronization.domain.passwordrecord.SyncPasswordRecord
import ru.dimagor555.synchronization.domain.request.InitialSyncRequest
import ru.dimagor555.synchronization.domain.response.SyncResponse
import ru.dimagor555.synchronization.usecase.rest.repository.ReceivePasswordRepository
import ru.dimagor555.synchronization.usecase.syncpassword.repository.SyncPasswordRepository
import ru.dimagor555.synchronization.usecase.syncresult.UpdateSyncResultUseCase

class ReceivePasswordRepositoryImpl(
    private val syncPasswordRepository: SyncPasswordRepository,
    private val updateSyncResult: UpdateSyncResultUseCase,
) : ReceivePasswordRepository {

    override suspend fun getSyncResponse(
        initialSyncRequest: InitialSyncRequest,
    ): SyncResponse {
        Napier.e("ReceivePasswordApiImpl getSyncPasswords 1")
        val existingRecords = syncPasswordRepository.getAllSyncRecords()
        Napier.e("ReceivePasswordApiImpl getSyncPasswords existingRecords = $existingRecords")
        val respondPasswords = getRespondPasswords(existingRecords, initialSyncRequest.passwordRecords)
        Napier.e("ReceivePasswordApiImpl getSyncPasswords respondPasswords = $respondPasswords")
        val requestPasswordIds = getRequestPasswordIds(existingRecords, initialSyncRequest.passwordRecords)
        Napier.e("ReceivePasswordApiImpl getSyncPasswords requestPasswordIds = $requestPasswordIds")

        return when {
            respondPasswords != null && requestPasswordIds.isNotEmpty() -> {
                Napier.e("ReceivePasswordApiImpl SimpleSyncResponse = ${SyncResponse.CommonSyncResponse(respondPasswords, requestPasswordIds)}")
                SyncResponse.CommonSyncResponse(respondPasswords, requestPasswordIds)
            }
            respondPasswords != null -> {
                Napier.e("ReceivePasswordApiImpl SimpleSyncResponse = ${SyncResponse.SimpleRespondSyncResponse(respondPasswords)}")
                SyncResponse.SimpleRespondSyncResponse(respondPasswords)
            }
            requestPasswordIds.isNotEmpty() -> {
                Napier.e("ReceivePasswordApiImpl SimpleSyncResponse = ${SyncResponse.SimpleRequestingSyncResponse(requestPasswordIds)}")
                SyncResponse.SimpleRequestingSyncResponse(requestPasswordIds)
            }
            else -> {
                SyncResponse.SuccessResponse
            }
        }
    }

    private suspend fun getRespondPasswords(
        existingRecords: List<SyncPasswordRecord>,
        syncPasswordRecords: List<SyncPasswordRecord>,
    ): JsonObject? {
        Napier.e("ReceivePasswordApiImpl getRespondPasswords 1")
        val respondAddRecordIds = getDifferentRecordIds(existingRecords, syncPasswordRecords)
        Napier.e("ReceivePasswordApiImpl getRespondPasswords respondAddRecordIds = $respondAddRecordIds")
        val respondUpdateRecordsIds =
            getSameRecords(existingRecords, syncPasswordRecords).map { it.id }
        Napier.e("ReceivePasswordApiImpl getRespondPasswords respondUpdateRecordsIds = $respondUpdateRecordsIds")
        Napier.e("ReceivePasswordApiImpl getRespondPasswords respondUpdateRecordsIds + respondAddRecordIds = ${respondUpdateRecordsIds + respondAddRecordIds}")

        val allRespondIds = respondAddRecordIds + respondUpdateRecordsIds

        return if (allRespondIds.isEmpty()) {
            null
        } else {
            val respondPasswords = syncPasswordRepository.getPasswordsAndFolderChildren(respondUpdateRecordsIds + respondAddRecordIds)
            Napier.e("ReceivePasswordApiImpl object2 = $respondPasswords")
            respondPasswords
        }
    }

    private fun getRequestPasswordIds(
        existingRecords: List<SyncPasswordRecord>,
        syncPasswordRecords: List<SyncPasswordRecord>,
    ): List<String> {
        Napier.e("ReceivePasswordApiImpl getRequestPasswordIds 1")
        val requestAddRecordIds = getDifferentRecordIds(syncPasswordRecords, existingRecords)
        Napier.e("ReceivePasswordApiImpl getRequestPasswordIds 2")
        val requestUpdateRecordIds = getSameRecords(syncPasswordRecords, existingRecords).map { it.id }
        Napier.e("ReceivePasswordApiImpl getRequestPasswordIds 3")
        val requestPasswordIds = requestAddRecordIds + requestUpdateRecordIds
        Napier.e("ReceivePasswordApiImpl getRequestPasswordIds requestPasswordIds = $requestPasswordIds")
        return requestPasswordIds
    }

    private fun getDifferentRecordIds(
        filteredRecords: List<SyncPasswordRecord>,
        comparedRecords: List<SyncPasswordRecord>,
    ): List<String> = filteredRecords.map { it.id }.filterNot {
        comparedRecords.map { it.id }.contains(it)
    }

    private fun getSameRecords(
        searchRecords: List<SyncPasswordRecord>,
        findRecords: List<SyncPasswordRecord>,
    ): List<SyncPasswordRecord> = searchRecords.mapNotNull { searchPassword ->
        val findRecord =
            findRecords.find { it.id == searchPassword.id } ?: return@mapNotNull null
        if (searchPassword.editingDateTime > findRecord.editingDateTime) {
            searchPassword
        } else {
            null
        }
    }
}