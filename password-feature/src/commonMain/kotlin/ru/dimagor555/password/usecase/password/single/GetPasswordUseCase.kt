package ru.dimagor555.password.usecase.password.single

import ru.dimagor555.password.usecase.password.repository.PasswordRepository
import ru.dimagor555.password.usecase.password.repository.getByIdOrThrowException

class GetPasswordUseCase(
    private val passwordRepository: PasswordRepository
) {
    suspend operator fun invoke(id: String) = passwordRepository.getByIdOrThrowException(id)
}
