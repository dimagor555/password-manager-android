package ru.dimagor555.password.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ru.dimagor555.password.domain.filter.PasswordFilter
import ru.dimagor555.password.repository.PasswordFilterRepository
import ru.dimagor555.password.repository.PasswordRepository

class ObservePasswordsUseCase(
    private val passwordRepository: PasswordRepository,
    private val passwordFilterRepository: PasswordFilterRepository
) {
    operator fun invoke() = createResultFlow()

    private fun createResultFlow() =
        observeFilteredPasswords()
            .flowOn(Dispatchers.Default)
            .conflate()

    private fun observeFilteredPasswords() = combine(
        passwordRepository.observeAll(),
        observePasswordFilter()
    ) { passwords, passwordFilter ->
        passwordFilter.filter(passwords)
    }

    private fun observePasswordFilter() = passwordFilterRepository.observePasswordFilterState()
        .map { PasswordFilter(it) }
}