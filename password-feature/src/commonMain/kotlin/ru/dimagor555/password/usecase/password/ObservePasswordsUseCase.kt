package ru.dimagor555.password.usecase.password

import kotlinx.coroutines.flow.Flow
import ru.dimagor555.password.domain.filter.toFilterParams
import ru.dimagor555.password.domain.password.Password
import ru.dimagor555.password.repository.FilterRepository
import ru.dimagor555.password.repository.PasswordRepository
import ru.dimagor555.password.usecase.ObserveChildrenUseCase

class ObservePasswordsUseCase(
    private val passwordRepository: PasswordRepository,
    filterRepository: FilterRepository,
) : ObserveChildrenUseCase<Password>(
    filterRepository = filterRepository,
    observeAll = { passwordRepository.observeAll() },
    associateChildren = { passwords -> passwords.associateBy { it.id } },
    mapChildrenToFilterParams = { passwords -> passwords.map { it.toFilterParams() } },
) {
    override operator fun invoke(): Flow<List<Password>> = createResultFlow()
}
