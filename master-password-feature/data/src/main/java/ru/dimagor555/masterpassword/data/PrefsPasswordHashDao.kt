package ru.dimagor555.masterpassword.data

import android.annotation.SuppressLint
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dimagor555.masterpassword.domain.PasswordHashDao
import javax.inject.Inject

internal class PrefsPasswordHashDao @Inject constructor(
    @ApplicationContext context: Context
) : PasswordHashDao {
    private val prefs = context.getSharedPreferences(PASSWORD_HASH_PREFS_NAME, Context.MODE_PRIVATE)

    override suspend fun hasPasswordHash() = withContext(Dispatchers.IO) {
        prefs.contains(PASSWORD_HASH_KEY)
    }

    override suspend fun getPasswordHash() = withContext(Dispatchers.IO) {
        val passwordHash = prefs.getString(PASSWORD_HASH_KEY, null)
        requireNotNull(passwordHash) { "Password hash does not exist yet" }
    }

    @SuppressLint("ApplySharedPref")
    override suspend fun setPasswordHash(passwordHash: String): Unit = withContext(Dispatchers.IO) {
        prefs.edit().run {
            putString(PASSWORD_HASH_KEY, passwordHash)
            commit()
        }
    }

    companion object {
        private const val PASSWORD_HASH_PREFS_NAME = "MasterPasswordHashPrefs"
        private const val PASSWORD_HASH_KEY = "MasterPasswordHash"
    }
}