package ru.dimagor555.masterpassword.usecase

import ru.dimagor555.masterpassword.repository.BiometryRepository

class IsBiometricLoginAvailableUseCase(
    private val biometryRepository: BiometryRepository
) {
    suspend operator fun invoke() = biometryRepository.canLogin()
}