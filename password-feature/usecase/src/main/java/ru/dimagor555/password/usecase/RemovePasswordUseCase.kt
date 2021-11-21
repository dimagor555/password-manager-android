package ru.dimagor555.password.usecase

import ru.dimagor555.password.repository.PasswordRepository

class RemovePasswordUseCase(
    private val passwordRepository: PasswordRepository
) {
    suspend operator fun invoke(passwordId: Int) {
        passwordRepository.remove(passwordId)
    }
}