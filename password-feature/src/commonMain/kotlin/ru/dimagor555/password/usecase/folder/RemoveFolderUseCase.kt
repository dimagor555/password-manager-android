package ru.dimagor555.password.usecase.folder

import ru.dimagor555.password.domain.folder.ChildId
import ru.dimagor555.password.repository.FolderRepository
import ru.dimagor555.password.usecase.folderchildren.repository.FolderChildParams
import ru.dimagor555.password.usecase.folderchildren.repository.FolderChildrenRepository
import ru.dimagor555.password.usecase.password.repository.PasswordRepository

class RemoveFolderUseCase(
    private val folderRepository: FolderRepository,
    private val folderChildrenRepository: FolderChildrenRepository,
    private val passwordRepository: PasswordRepository,
) {
    suspend operator fun invoke(folderId: String, parentId: String) {
        folderRepository.remove(folderId)
        val folderPasswordIds = folderChildrenRepository.getFolderPasswords(folderId).toSet()
        passwordRepository.removeAllByIds(folderPasswordIds)
        folderChildrenRepository.remove(folderId)
        folderChildrenRepository.removeChildFromFolder(
            FolderChildParams(
                parentId = parentId,
                childId = ChildId.FolderId(folderId),
            ),
        )
    }
}
