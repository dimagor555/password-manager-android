package ru.dimagor555.masterpassword.usecase

import ru.dimagor555.masterpassword.domain.MasterPasswordRepository

class HasMasterPasswordUseCase(
    private val masterPasswordRepository: MasterPasswordRepository
) {
    suspend operator fun invoke() = masterPasswordRepository.hasPassword()
}