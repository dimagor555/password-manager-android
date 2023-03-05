package ru.dimagor555.syncpassintegration.data

import kotlinx.coroutines.flow.firstOrNull
import ru.dimagor555.password.domain.password.Password
import ru.dimagor555.password.repository.PasswordRepository
import ru.dimagor555.synchronization.domain.SyncPasswordRecord
import ru.dimagor555.synchronization.repository.SyncPasswordRepository
import ru.dimagor555.syncpassintegration.domain.parseToString
import ru.dimagor555.syncpassintegration.domain.parseToValue
import ru.dimagor555.syncpassintegration.domain.toSyncPasswordRecords

class SyncPasswordRepositoryImpl(
    private val passwordRepository: PasswordRepository,
) : SyncPasswordRepository {

    override suspend fun getAllSyncRecords(): List<SyncPasswordRecord> {
        val passwords = passwordRepository.observeAll().firstOrNull() ?: emptyList()
        return passwords.toSyncPasswordRecords()
    }

    override suspend fun getAllSyncRecordsString(): String {
        val passwords = passwordRepository.observeAll().firstOrNull() ?: emptyList()
        return parseToString(passwords.toSyncPasswordRecords())
    }

    override suspend fun getSyncRecordsStringByIds(ids: List<String>): String {
        val passwords = getPasswordsByIds(ids)
        return parseToString(passwords.toSyncPasswordRecords())
    }

    override suspend fun getPasswordsStringByIds(ids: List<String>): String {
        val passwords = getPasswordsByIds(ids)
        return parseToString(passwords)
    }

    private suspend fun getPasswordsByIds(ids: List<String>): List<Password> =
        ids.mapNotNull { passwordRepository.getById(it) }

    override suspend fun addPasswords(value: String) {
        putPasswords(value) {
            passwordRepository.add(it)
        }
    }

    override suspend fun updatePasswords(value: String) {
        putPasswords(value) {
            passwordRepository.update(it)
        }
    }

    private suspend fun putPasswords(value: String, action: suspend (password: Password) -> Unit) {
        if (value.isNotEmpty()) {
            val passwords = parseToValue<List<Password>>(value)
            if (passwords.isNotEmpty()) {
                passwords.forEach {
                    action(it)
                }
            }
        }
    }
}
