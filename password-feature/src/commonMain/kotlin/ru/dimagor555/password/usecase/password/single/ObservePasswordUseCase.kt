package ru.dimagor555.password.usecase.password.single

import kotlinx.coroutines.flow.Flow
import ru.dimagor555.password.domain.password.Password
import ru.dimagor555.password.usecase.password.repository.PasswordRepository

class ObservePasswordUseCase(
    private val passwordRepository: PasswordRepository
) {
    operator fun invoke(id: String): Flow<Password?> = passwordRepository.observeById(id)
}
