package ru.dimagor555.password.usecase

import ru.dimagor555.password.domain.Password
import ru.dimagor555.password.repository.PasswordRepository

class RemovePasswordUseCase(
    private val passwordRepository: PasswordRepository
) {
    suspend operator fun invoke(password: Password) {
        passwordRepository.remove(password)
    }
}