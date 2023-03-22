package ru.dimagor555.password.usecase.filter

import ru.dimagor555.password.domain.filter.FilterState
import ru.dimagor555.password.repository.FilterRepository

class UpdateFilterStateUseCase(
    private val filterRepository: FilterRepository
) {
    suspend operator fun invoke(filterState: FilterState) =
        filterRepository.updateFilterState(filterState)
}