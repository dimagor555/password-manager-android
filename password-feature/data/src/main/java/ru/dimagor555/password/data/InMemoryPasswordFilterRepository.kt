package ru.dimagor555.password.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import ru.dimagor555.password.domain.filter.PasswordFilterState
import ru.dimagor555.password.repository.PasswordFilterRepository
import javax.inject.Inject

internal class InMemoryPasswordFilterRepository @Inject constructor() : PasswordFilterRepository {
    private val passwordFilterState = MutableStateFlow(PasswordFilterState())

    override fun observePasswordFilterState(): Flow<PasswordFilterState> = passwordFilterState

    override suspend fun updatePasswordFilterState(filterState: PasswordFilterState) {
        passwordFilterState.value = filterState
    }
}