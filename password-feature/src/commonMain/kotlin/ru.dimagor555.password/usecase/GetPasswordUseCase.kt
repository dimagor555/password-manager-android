package ru.dimagor555.password.usecase

import ru.dimagor555.password.repository.PasswordRepository
import ru.dimagor555.password.repository.getByIdOrThrowException

class GetPasswordUseCase(
    private val passwordRepository: PasswordRepository
) {
    suspend operator fun invoke(id: Int) = passwordRepository.getByIdOrThrowException(id)
}