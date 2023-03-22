package ru.dimagor555.password.usecase.folder

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dimagor555.password.usecase.folderchildren.repository.ChangeFolderParams
import ru.dimagor555.password.usecase.folderchildren.repository.FolderChildrenRepository

class ChangeFolderParentUseCase(
    private val folderChildrenRepository: FolderChildrenRepository,
) {

    suspend operator fun invoke(params: ChangeFolderParams) =
        withContext(Dispatchers.Default) {
            folderChildrenRepository.changeChildFolder(params)
        }
}
