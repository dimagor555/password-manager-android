package ru.dimagor555.password.usecase.folder

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dimagor555.password.domain.folder.Folder
import ru.dimagor555.password.repository.FolderRepository
import ru.dimagor555.password.repository.getByIdOrThrowException

class ChangeFolderParentUseCase(
    private val folderRepository: FolderRepository,
) {

    suspend operator fun invoke(folderId: String, parentId: String) = withContext(Dispatchers.Default) {
        val oldFolder = folderRepository.getByIdOrThrowException(folderId)
        val newFolder = oldFolder.changeParent(parentId)
        folderRepository.update(newFolder)
    }

    private fun Folder.changeParent(parentId: String) = copy(
        parentId = parentId,
    )
}