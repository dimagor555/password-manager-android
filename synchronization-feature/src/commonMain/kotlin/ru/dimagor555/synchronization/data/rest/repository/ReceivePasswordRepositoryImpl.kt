package ru.dimagor555.synchronization.data.rest.repository

import kotlinx.serialization.json.JsonObject
import ru.dimagor555.synchronization.domain.passwordrecord.SyncPasswordRecord
import ru.dimagor555.synchronization.domain.request.InitialSyncRequest
import ru.dimagor555.synchronization.domain.response.SyncResponse
import ru.dimagor555.synchronization.domain.response.SyncResponse.*
import ru.dimagor555.synchronization.usecase.rest.repository.ReceivePasswordRepository
import ru.dimagor555.synchronization.usecase.syncpassword.repository.SyncPasswordRepository

class ReceivePasswordRepositoryImpl(
    private val syncPasswordRepository: SyncPasswordRepository,
) : ReceivePasswordRepository {

    override suspend fun getSyncResponse(initialSyncRequest: InitialSyncRequest): SyncResponse {
        val existingRecords = syncPasswordRepository.getAllSyncRecords()
        val respondPasswords =
            getRespondPasswords(existingRecords, initialSyncRequest.passwordRecords)
        val requestPasswordIds =
            getRequestPasswordIds(existingRecords, initialSyncRequest.passwordRecords)

        return when {
            respondPasswords != null && requestPasswordIds.isNotEmpty() -> {
                CommonSyncResponse(respondPasswords, requestPasswordIds)
            }
            respondPasswords != null -> {
                SimpleRespondSyncResponse(respondPasswords)
            }
            requestPasswordIds.isNotEmpty() -> {
                SimpleRequestingSyncResponse(requestPasswordIds)
            }
            else -> {
                SuccessResponse
            }
        }
    }

    private suspend fun getRespondPasswords(
        existingRecords: List<SyncPasswordRecord>,
        syncPasswordRecords: List<SyncPasswordRecord>,
    ): JsonObject? {
        val respondAddRecordIds = getDifferentRecordIds(existingRecords, syncPasswordRecords)
        val respondUpdateRecordsIds = getSameRecordsIds(existingRecords, syncPasswordRecords)
        val allRespondIds = respondAddRecordIds + respondUpdateRecordsIds
        return when (allRespondIds.isEmpty()) {
            true -> null
            false -> syncPasswordRepository.getPasswordsAndFolderChildren(allRespondIds)
        }
    }

    private fun getRequestPasswordIds(
        existingRecords: List<SyncPasswordRecord>,
        syncPasswordRecords: List<SyncPasswordRecord>,
    ): List<String> {
        val requestAddRecordIds = getDifferentRecordIds(syncPasswordRecords, existingRecords)
        val requestUpdateRecordIds = getSameRecordsIds(syncPasswordRecords, existingRecords)
        return requestAddRecordIds + requestUpdateRecordIds
    }

    private fun getDifferentRecordIds(
        filteredRecords: List<SyncPasswordRecord>,
        comparedRecords: List<SyncPasswordRecord>,
    ): List<String> {
        val filteredRecordIds = filteredRecords.map { it.id }
        val comparedRecordIds = comparedRecords.map { it.id }
        return filteredRecordIds
            .filterNot { comparedRecordIds.contains(it) }
    }

    private fun getSameRecordsIds(
        searchRecords: List<SyncPasswordRecord>,
        findRecords: List<SyncPasswordRecord>,
    ): List<String> = searchRecords
        .mapNotNull { searchPassword ->
            determinePresenceOfPassword(searchPassword, findRecords)
        }.map { it.id }

    private fun determinePresenceOfPassword(
        searchRecord: SyncPasswordRecord,
        findRecords: List<SyncPasswordRecord>,
    ): SyncPasswordRecord? {
        val findRecord = findRecords.find { it.id == searchRecord.id } ?: return null
        return when {
            searchRecord.editingDateTime > findRecord.editingDateTime -> searchRecord
            else -> null
        }
    }
}