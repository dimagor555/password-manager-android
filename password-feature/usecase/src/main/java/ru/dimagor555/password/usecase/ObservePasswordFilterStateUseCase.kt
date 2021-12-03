package ru.dimagor555.password.usecase

import ru.dimagor555.password.repository.PasswordFilterRepository

class ObservePasswordFilterStateUseCase(
    private val passwordFilterRepository: PasswordFilterRepository
) {
    operator fun invoke() = passwordFilterRepository.observePasswordFilterState()
}