package ru.dimagor555.masterpassword.usecase.biometric

import ru.dimagor555.masterpassword.usecase.biometric.repository.BiometricAvailabilityRepository

internal class IsBiometricAvailableUsecase(
    private val availabilityRepository: BiometricAvailabilityRepository,
) {

    operator fun invoke(): Boolean =
        availabilityRepository.isBiometricAvailable()
}