package ru.dimagor555.password.usecase.folder

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dimagor555.password.repository.FolderChildrenRepository

class ChangeFolderParentUseCase(
    private val folderChildrenRepository: FolderChildrenRepository,
) {

    suspend operator fun invoke(folderId: String, fromId: String, toId: String) =
        withContext(Dispatchers.Default) {
            folderChildrenRepository.replaceChildLocation(folderId, fromId, toId)
        }
}
