package ru.dimagor555.password.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import ru.dimagor555.core.DataState
import ru.dimagor555.password.domain.Password
import ru.dimagor555.password.repository.PasswordRepository

class ObservePasswordUseCase(
    private val passwordRepository: PasswordRepository
) {
    operator fun invoke(id: Int): Flow<DataState<Password>> = flow {
        emit(DataState.Loading())
        passwordRepository.observeById(id).collect {
            emit(DataState.Data(it))
        }
    }
}