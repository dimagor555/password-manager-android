package ru.dimagor555.synchronization.data

import ru.dimagor555.synchronization.domain.SyncPasswordRecord
import ru.dimagor555.synchronization.domain.SyncPasswords
import ru.dimagor555.synchronization.repository.ReceivePasswordApi
import ru.dimagor555.synchronization.repository.SyncPasswordRepository

class ReceivePasswordApiImpl(
    private val syncPasswordRepository: SyncPasswordRepository,
) : ReceivePasswordApi {

    override suspend fun getSyncPasswords(
        syncPasswordRecords: List<SyncPasswordRecord>,
    ): SyncPasswords {
        val syncPasswords = SyncPasswords()
        val existingRecords = syncPasswordRepository.getAllSyncRecords()

        val respondAddRecords = getDifferentRecordIds(existingRecords, syncPasswordRecords)
        val requestAddIds = getDifferentRecordIds(syncPasswordRecords, existingRecords)
        val respondUpdateRecords = getSameRecords(existingRecords, syncPasswordRecords)
        val requestUpdateRecords = getSameRecords(syncPasswordRecords, existingRecords)

        val requestAddRecords = syncPasswordRecords.filter {record ->
            requestAddIds.find { record.id == it } != null
        }

        syncPasswords.respondToAddPasswords =
            syncPasswordRepository.getPasswordsStringByIds(respondAddRecords)
        syncPasswords.respondToUpdatePasswords =
            syncPasswordRepository.getPasswordsStringByIds(respondUpdateRecords.map { it.id })
        syncPasswords.requestToAddRecords = requestAddRecords
        syncPasswords.requestToUpdateRecords = requestUpdateRecords

        return syncPasswords
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

    override suspend fun addPasswords(syncPasswords: SyncPasswords) {
        if (syncPasswords.respondToAddPasswords != null) {
            syncPasswordRepository.addPasswords(syncPasswords.respondToAddPasswords!!)
        }
        if (syncPasswords.respondToUpdatePasswords != null) {
            syncPasswordRepository.updatePasswords(syncPasswords.respondToUpdatePasswords!!)
        }
    }
}
