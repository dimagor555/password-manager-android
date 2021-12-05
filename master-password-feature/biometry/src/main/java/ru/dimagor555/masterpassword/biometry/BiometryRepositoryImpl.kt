package ru.dimagor555.masterpassword.biometry

import android.content.Context
import androidx.biometric.BiometricManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dimagor555.masterpassword.repository.BiometryRepository
import javax.inject.Inject

internal class BiometryRepositoryImpl @Inject constructor(
    @ApplicationContext context: Context
) : BiometryRepository {
    private val biometricManager = BiometricManager.from(context)

    override suspend fun canLogin() = withContext(Dispatchers.IO) {
        biometricManager.canAuthenticate(DEFAULT_AUTHENTICATOR) == BiometricManager.BIOMETRIC_SUCCESS
    }

    companion object {
        const val DEFAULT_AUTHENTICATOR = BiometricManager.Authenticators.BIOMETRIC_WEAK
    }
}