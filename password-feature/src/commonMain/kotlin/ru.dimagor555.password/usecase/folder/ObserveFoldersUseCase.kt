package ru.dimagor555.password.usecase.folder

import kotlinx.coroutines.flow.Flow
import ru.dimagor555.password.domain.filter.toFilterParams
import ru.dimagor555.password.domain.folder.Folder
import ru.dimagor555.password.repository.FilterRepository
import ru.dimagor555.password.repository.FolderRepository
import ru.dimagor555.password.usecase.ObserveChildrenUseCase

class ObserveFoldersUseCase(
    private val folderRepository: FolderRepository,
    filterRepository: FilterRepository,
) : ObserveChildrenUseCase<Folder>(
    filterRepository = filterRepository,
    observeAll = { folderRepository.observeAll() },
    associateChildren = { folders -> folders.associateBy { it.id } },
    mapChildrenToFilterParams = { folders -> folders.map { it.toFilterParams() } },
) {
    override operator fun invoke(): Flow<List<Folder>> = createResultFlow()
}
