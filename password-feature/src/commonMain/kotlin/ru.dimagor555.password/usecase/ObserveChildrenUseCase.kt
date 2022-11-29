package ru.dimagor555.password.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.dimagor555.password.domain.filter.FilterParams
import ru.dimagor555.password.domain.filter.Filter
import ru.dimagor555.password.repository.FilterRepository

abstract class ObserveChildrenUseCase<T>(
    private val filterRepository: FilterRepository,
    private val observeAll: () -> Flow<List<T>>,
    private val associateChildren: (items: List<T>) -> Map<String?, T>,
    private val mapChildrenToFilterParams: (items: List<T>) -> List<FilterParams>,
) {

    abstract fun invoke(): Flow<List<T>>

    fun createResultFlow(): Flow<List<T>> =
        observeFilteredChildren()
            .flowOn(Dispatchers.Default)
            .conflate()

    private fun observeFilteredChildren(): Flow<List<T>> = combine(
        observeAll(),
        observeFilter()
    ) { children, passwordFilter ->
        val childrenIds = getChildrenIds(children, passwordFilter)
        val childrenByIds = associateChildren(children)
        sortChildrenByIds(childrenByIds, childrenIds)
    }

    private fun getChildrenIds(
        children: List<T>,
        filter: Filter
    ): List<String?> = filter.filter(
        mapChildrenToFilterParams(children)
    )

    private fun sortChildrenByIds(
        children: Map<String?, T>,
        filteredIds: List<String?>,
    ): List<T> = filteredIds.mapNotNull {
        children[it]
    }

    private fun observeFilter(): Flow<Filter> =
        filterRepository.observePasswordFilterState()
            .map { Filter(it) }
}