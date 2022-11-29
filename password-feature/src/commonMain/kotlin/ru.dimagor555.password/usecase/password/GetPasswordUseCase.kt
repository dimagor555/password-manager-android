package ru.dimagor555.password.usecase.password

import ru.dimagor555.password.repository.PasswordRepository
import ru.dimagor555.password.repository.getByIdOrThrowException

class GetPasswordUseCase(
    private val passwordRepository: PasswordRepository
) {
    suspend operator fun invoke(id: String) = passwordRepository.getByIdOrThrowException(id)
}