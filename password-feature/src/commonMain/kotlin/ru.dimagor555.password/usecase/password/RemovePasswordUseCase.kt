package ru.dimagor555.password.usecase.password

import ru.dimagor555.password.domain.folder.ChildId
import ru.dimagor555.password.repository.FolderChildrenRepository
import ru.dimagor555.password.repository.PasswordRepository

class RemovePasswordUseCase(
    private val passwordRepository: PasswordRepository,
    private val folderChildrenRepository: FolderChildrenRepository,
) {
    suspend operator fun invoke(passwordId: String, parentId: String) {
        passwordRepository.remove(passwordId)
        folderChildrenRepository.removeChildFromFolderChildren(
            parentId,
            ChildId.PasswordId(passwordId)
        )
    }
}
