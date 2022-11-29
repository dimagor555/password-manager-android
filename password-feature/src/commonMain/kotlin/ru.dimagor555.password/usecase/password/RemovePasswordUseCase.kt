package ru.dimagor555.password.usecase.password

import ru.dimagor555.password.repository.PasswordRepository

class RemovePasswordUseCase(
    private val passwordRepository: PasswordRepository
) {
    suspend operator fun invoke(passwordId: String) {
        passwordRepository.remove(passwordId)
    }
}