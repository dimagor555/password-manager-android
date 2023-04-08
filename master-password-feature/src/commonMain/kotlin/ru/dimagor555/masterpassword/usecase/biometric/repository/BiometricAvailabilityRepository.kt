 package ru.dimagor555.masterpassword.usecase.biometric.repository

internal interface BiometricAvailabilityRepository {

    fun isBiometricAvailable(): Boolean
}