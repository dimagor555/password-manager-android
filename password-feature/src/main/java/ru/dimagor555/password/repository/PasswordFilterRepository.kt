package ru.dimagor555.password.repository

import kotlinx.coroutines.flow.Flow
import ru.dimagor555.password.domain.filter.PasswordFilterState

interface PasswordFilterRepository {
    fun observePasswordFilterState(): Flow<PasswordFilterState>

    suspend fun updatePasswordFilterState(filterState: PasswordFilterState)
}