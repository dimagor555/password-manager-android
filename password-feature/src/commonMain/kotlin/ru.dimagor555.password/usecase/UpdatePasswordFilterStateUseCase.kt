package ru.dimagor555.password.usecase

import ru.dimagor555.password.domain.filter.PasswordFilterState
import ru.dimagor555.password.repository.PasswordFilterRepository

class UpdatePasswordFilterStateUseCase(
    private val passwordFilterRepository: PasswordFilterRepository
) {
    suspend operator fun invoke(filterState: PasswordFilterState) =
        passwordFilterRepository.updatePasswordFilterState(filterState)
}