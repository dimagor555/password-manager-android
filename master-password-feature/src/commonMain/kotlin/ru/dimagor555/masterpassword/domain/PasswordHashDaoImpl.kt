package ru.dimagor555.masterpassword.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dimagor555.masterpassword.domain.SettingsRepository.Properties.MASTER_PASSWORD_HASH

class PasswordHashDaoImpl(
    private val settingsRepository: SettingsRepository,
) : PasswordHashDao {

    override suspend fun hasPasswordHash(): Boolean = withContext(Dispatchers.IO) {
        settingsRepository.has(MASTER_PASSWORD_HASH)
    }

    override suspend fun getPasswordHash(): String = withContext(Dispatchers.IO) {
        val passwordHash = settingsRepository.get(MASTER_PASSWORD_HASH)
        requireNotNull(passwordHash) { "Password hash does not exist yet" }
    }

    override suspend fun setPasswordHash(passwordHash: String) = withContext(Dispatchers.IO) {
        settingsRepository.set(MASTER_PASSWORD_HASH, passwordHash)
    }
}
