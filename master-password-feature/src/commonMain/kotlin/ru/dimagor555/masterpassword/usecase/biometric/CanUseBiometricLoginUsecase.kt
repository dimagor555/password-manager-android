package ru.dimagor555.masterpassword.usecase.biometric

import ru.dimagor555.masterpassword.usecase.biometric.repository.BiometricSymmetricKeyRepository

internal class CanUseBiometricLoginUsecase(
    private val biometricSymmetricKeyRepository: BiometricSymmetricKeyRepository,
    private val isBiometricAvailable: IsBiometricAvailableUsecase,
) {

    suspend operator fun invoke(): Boolean {
        val hasBiometricSymmetricKey = biometricSymmetricKeyRepository.getOrNull() != null
        return hasBiometricSymmetricKey && isBiometricAvailable()
    }
}