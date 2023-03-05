package ru.dimagor555.synchronization.usecase

import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dimagor555.synchronization.data.ConnectionAddress
import ru.dimagor555.synchronization.data.SendPasswordApiImpl
import ru.dimagor555.synchronization.domain.SyncDevice

class PostSyncPasswordRecordUseCase(
    private val connectionAddress: ConnectionAddress,
    private val sendPasswordApiImpl: SendPasswordApiImpl,
) {
    suspend operator fun invoke(syncDevice: SyncDevice): HttpStatusCode = withContext(Dispatchers.IO) {
        connectionAddress.ip = syncDevice.address
        syncRecords()
    }

    private suspend fun syncRecords(): HttpStatusCode {
        val syncPasswords = sendPasswordApiImpl.postSyncPasswordRecord()
        sendPasswordApiImpl.addPasswords(syncPasswords)
        return sendPasswordApiImpl.postRequestPasswords(syncPasswords)
    }
}
