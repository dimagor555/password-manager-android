package ru.dimagor555.password.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.dimagor555.core.DataState
import ru.dimagor555.password.domain.filter.PasswordFilter
import ru.dimagor555.password.repository.PasswordFilterRepository
import ru.dimagor555.password.repository.PasswordRepository

class ObservePasswordsUseCase(
    private val passwordRepository: PasswordRepository,
    private val passwordFilterRepository: PasswordFilterRepository
) {
    operator fun invoke() = createResultFlow()
        .flowOn(Dispatchers.Default)
        .conflate()

    private fun createResultFlow() = flow {
        emit(DataState.Loading())
        observeFilteredPasswords().collect {
            emit(DataState.Data(it))
        }
    }

    private fun observeFilteredPasswords() = combine(
        passwordRepository.observeAll(),
        observePasswordFilter()
    ) { passwords, passwordFilter ->
        passwordFilter.filter(passwords)
    }

    private fun observePasswordFilter() = passwordFilterRepository.observePasswordFilterState()
        .map { PasswordFilter(it) }
}