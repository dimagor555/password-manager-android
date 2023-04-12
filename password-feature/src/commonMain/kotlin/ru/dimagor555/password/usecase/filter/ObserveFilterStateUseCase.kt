package ru.dimagor555.password.usecase.filter

import ru.dimagor555.password.usecase.filter.repository.FilterRepository

class ObserveFilterStateUseCase(
    private val filterRepository: FilterRepository
) {
    operator fun invoke() = filterRepository.observeFilterState()
}