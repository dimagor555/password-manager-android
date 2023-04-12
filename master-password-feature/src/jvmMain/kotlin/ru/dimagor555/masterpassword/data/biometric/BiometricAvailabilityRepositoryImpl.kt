package ru.dimagor555.masterpassword.data.biometric

import ru.dimagor555.masterpassword.usecase.biometric.repository.BiometricAvailabilityRepository

internal class BiometricAvailabilityRepositoryImpl : BiometricAvailabilityRepository {

    override fun isBiometricAvailable(): Boolean {
        return false
    }
}