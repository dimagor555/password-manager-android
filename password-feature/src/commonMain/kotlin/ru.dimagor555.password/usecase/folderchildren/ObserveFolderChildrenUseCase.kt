package ru.dimagor555.password.usecase.folderchildren

import kotlinx.coroutines.flow.Flow
import ru.dimagor555.password.domain.folder.FolderChildren
import ru.dimagor555.password.repository.FolderChildrenRepository

class ObserveFolderChildrenUseCase(
    private val folderChildrenRepository: FolderChildrenRepository,
) {
    suspend operator fun invoke(id: String): Flow<FolderChildren?> =
        folderChildrenRepository.observeById(id)
}
