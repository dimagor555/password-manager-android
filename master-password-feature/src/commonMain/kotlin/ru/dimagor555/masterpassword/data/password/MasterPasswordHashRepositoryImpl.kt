package ru.dimagor555.masterpassword.data.password

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dimagor555.masterpassword.usecase.password.repository.MasterPasswordHashRepository

internal class MasterPasswordHashRepositoryImpl : MasterPasswordHashRepository {

    private val settings = Settings()

    override suspend fun hasPasswordHash(): Boolean = withContext(Dispatchers.IO) {
        settings.hasKey(MASTER_PASSWORD_HASH)
    }

    override suspend fun getPasswordHash(): String = withContext(Dispatchers.IO) {
        val passwordHash = settings.getStringOrNull(MASTER_PASSWORD_HASH)
        requireNotNull(passwordHash) { "No master password hash" }
    }

    override suspend fun setPasswordHash(passwordHash: String) = withContext(Dispatchers.IO) {
        settings[MASTER_PASSWORD_HASH] = passwordHash
    }

    companion object {
        private const val MASTER_PASSWORD_HASH = "MASTER_PASSWORD_HASH"
    }
}
