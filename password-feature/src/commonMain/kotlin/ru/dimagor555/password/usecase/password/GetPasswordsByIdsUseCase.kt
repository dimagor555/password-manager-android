package ru.dimagor555.password.usecase.password

import ru.dimagor555.password.domain.password.Password
import ru.dimagor555.password.repository.PasswordRepository

class GetPasswordsByIdsUseCase(
    private val passwordRepository: PasswordRepository,
) {
    suspend operator fun invoke(ids: Set<String>): List<Password> =
        passwordRepository.getAllByIds(ids)
}
