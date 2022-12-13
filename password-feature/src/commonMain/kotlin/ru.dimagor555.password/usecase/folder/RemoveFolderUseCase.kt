package ru.dimagor555.password.usecase.folder

import ru.dimagor555.password.domain.folder.ChildId
import ru.dimagor555.password.repository.FolderChildrenRepository
import ru.dimagor555.password.repository.FolderRepository
import ru.dimagor555.password.repository.PasswordRepository

class RemoveFolderUseCase(
    private val folderRepository: FolderRepository,
    private val folderChildrenRepository: FolderChildrenRepository,
    private val passwordRepository: PasswordRepository,
) {
    suspend operator fun invoke(folderId: String, parentId: String) {
        folderRepository.remove(folderId)
        passwordRepository.removeFolderPasswords(folderChildrenRepository.getFolderPasswords(folderId))
        folderChildrenRepository.remove(folderId)
        folderChildrenRepository.removeChildFromFolderChildren(parentId, ChildId.FolderId(folderId))
    }
}
