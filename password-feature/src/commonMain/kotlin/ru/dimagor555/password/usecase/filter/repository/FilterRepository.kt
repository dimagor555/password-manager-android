package ru.dimagor555.password.usecase.filter.repository

import kotlinx.coroutines.flow.Flow
import ru.dimagor555.password.domain.filter.FilterState

interface FilterRepository {

    fun observeFilterState(): Flow<FilterState>

    suspend fun updateFilterState(filterState: FilterState)
}