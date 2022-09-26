package ru.dimagor555.masterpassword.usecase

import ru.dimagor555.masterpassword.domain.MasterPasswordRepository

class SetMasterPasswordUseCase(
    private val masterPasswordRepository: MasterPasswordRepository
) {
    suspend operator fun invoke(password: String) = masterPasswordRepository.setPassword(password)
}