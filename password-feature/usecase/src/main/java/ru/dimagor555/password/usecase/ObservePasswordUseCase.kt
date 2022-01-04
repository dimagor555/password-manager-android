package ru.dimagor555.password.usecase

import kotlinx.coroutines.flow.Flow
import ru.dimagor555.password.domain.Password
import ru.dimagor555.password.repository.PasswordRepository

class ObservePasswordUseCase(
    private val passwordRepository: PasswordRepository
) {
    operator fun invoke(id: Int): Flow<Password?> = passwordRepository.observeById(id)
}