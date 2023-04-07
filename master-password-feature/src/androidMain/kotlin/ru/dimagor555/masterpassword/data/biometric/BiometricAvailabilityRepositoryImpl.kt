package ru.dimagor555.masterpassword.data.biometric

import android.content.Context
import androidx.biometric.BiometricManager
import ru.dimagor555.masterpassword.usecase.biometric.repository.BiometricAvailabilityRepository

internal class BiometricAvailabilityRepositoryImpl(
    private val context: Context,
) : BiometricAvailabilityRepository {

    override fun isBiometricAvailable(): Boolean =
        BiometricManager
            .from(context)
            .canAuthenticate(BiometricConstants.AUTHENTICATOR)
            .let { it == BiometricManager.BIOMETRIC_SUCCESS }
}