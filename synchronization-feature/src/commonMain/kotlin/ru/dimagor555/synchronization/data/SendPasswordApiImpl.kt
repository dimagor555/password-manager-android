package ru.dimagor555.synchronization.data

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import ru.dimagor555.synchronization.domain.SyncPasswordRecord
import ru.dimagor555.synchronization.domain.SyncPasswords
import ru.dimagor555.synchronization.repository.ClientRepository
import ru.dimagor555.synchronization.repository.SendPasswordApi
import ru.dimagor555.synchronization.repository.SyncPasswordRepository

class SendPasswordApiImpl(
    clientRepository: ClientRepository,
    private val connectionAddress: ConnectionAddress,
    private val syncPasswordRepository: SyncPasswordRepository,
) : SendPasswordApi {
    private val client = clientRepository.createClient()

    override suspend fun postSyncPasswordRecord(): SyncPasswords {
        val passwordsString = syncPasswordRepository.getAllSyncRecords()
        val syncPasswords = runCatching {
            client.post("${connectionAddress.ip}/${SyncPasswordRecord.path}") {
                contentType(ContentType.Application.Json)
                setBody(passwordsString)
            }.body<SyncPasswords>()
        }.getOrNull() ?: SyncPasswords()
        return syncPasswords
    }

    override suspend fun addPasswords(syncPasswords: SyncPasswords) {
        if (syncPasswords.respondToAddPasswords != null) {
            syncPasswordRepository.addPasswords(syncPasswords.respondToAddPasswords!!)
        }
        if (syncPasswords.respondToUpdatePasswords != null) {
            syncPasswordRepository.updatePasswords(syncPasswords.respondToUpdatePasswords!!)
        }
    }

    override suspend fun postRequestPasswords(syncPasswords: SyncPasswords): HttpStatusCode {
        val respondSyncPasswords = getRespondSyncRecords(syncPasswords)
        val syncStatusCode = runCatching {
            client.post("${connectionAddress.ip}/${SyncPasswordRecord.path}/syncPasswords") {
                contentType(ContentType.Application.Json)
                setBody(respondSyncPasswords)
            }.body<HttpStatusCode>()
        }.getOrNull()
        return syncStatusCode ?: HttpStatusCode.BadRequest
    }

    private suspend fun getRespondSyncRecords(syncPasswords: SyncPasswords): SyncPasswords {
        val respondSyncPasswords = SyncPasswords()
        if (syncPasswords.requestToAddRecords != null) {
            respondSyncPasswords.respondToAddPasswords =
                syncPasswordRepository.getPasswordsStringByIds(
                    syncPasswords.requestToAddRecords!!.map { it.id }
                )
        }
        if (syncPasswords.requestToUpdateRecords != null) {
            respondSyncPasswords.respondToUpdatePasswords =
                syncPasswordRepository.getPasswordsStringByIds(
                    syncPasswords.requestToUpdateRecords!!.map { it.id }
                )
        }
        return respondSyncPasswords
    }
}
