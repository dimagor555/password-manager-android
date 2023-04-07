package ru.dimagor555.masterpassword.data.biometric

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dimagor555.masterpassword.usecase.biometric.repository.BiometricSymmetricKeyRepository

internal class BiometricSymmetricKeyRepositoryImpl : BiometricSymmetricKeyRepository {

    private val settings = Settings()

    override suspend fun getOrNull(): String? = withContext(Dispatchers.IO) {
        settings.getStringOrNull(BIOMETRIC_SYMMETRIC_KEY)
    }

    override suspend fun set(symmetricKey: String?) = withContext(Dispatchers.IO) {
        settings[BIOMETRIC_SYMMETRIC_KEY] = symmetricKey
    }

    companion object {
        private const val BIOMETRIC_SYMMETRIC_KEY = "BIOMETRIC_SYMMETRIC_KEY"
    }
}