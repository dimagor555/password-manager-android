package ru.dimagor555.masterpassword.usecase

import ru.dimagor555.masterpassword.domain.MasterPasswordRepository

class LoginByPasswordUseCase(
    private val masterPasswordRepository: MasterPasswordRepository
) {
    suspend operator fun invoke(password: String) =
        masterPasswordRepository.verifyPassword(password)
}