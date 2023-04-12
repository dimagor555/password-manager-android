package ru.dimagor555.password.data.filter

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import ru.dimagor555.password.domain.filter.FilterState
import ru.dimagor555.password.usecase.filter.repository.FilterRepository

internal class InMemoryPasswordFilterRepository : FilterRepository {
    private val passwordFilterState = MutableStateFlow(FilterState())

    override fun observeFilterState(): Flow<FilterState> = passwordFilterState

    override suspend fun updateFilterState(filterState: FilterState) {
        passwordFilterState.value = filterState
    }
}
