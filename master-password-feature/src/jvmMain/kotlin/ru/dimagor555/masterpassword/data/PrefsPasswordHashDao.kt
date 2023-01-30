package ru.dimagor555.masterpassword.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dimagor555.masterpassword.domain.PasswordHashDao
import java.util.prefs.Preferences

internal class PrefsPasswordHashDao : PasswordHashDao {
    private val prefs = Preferences.userRoot().node(PASSWORD_HASH_PREFS_NAME)

    override suspend fun hasPasswordHash(): Boolean = withContext(Dispatchers.IO) {
        prefs.nodeExists(PASSWORD_HASH_KEY)
    }

    override suspend fun getPasswordHash(): String = withContext(Dispatchers.IO) {
        val passwordHash = prefs.get(PASSWORD_HASH_KEY, null)
        requireNotNull(passwordHash) { "Password hash does not exist yet" }
    }

    override suspend fun setPasswordHash(passwordHash: String): Unit = withContext(Dispatchers.IO) {
        prefs.put(PASSWORD_HASH_KEY, passwordHash)
    }

    companion object {
        private const val PASSWORD_HASH_PREFS_NAME = "MasterPasswordHashPrefs"
        private const val PASSWORD_HASH_KEY = "MasterPasswordHash"
    }
}