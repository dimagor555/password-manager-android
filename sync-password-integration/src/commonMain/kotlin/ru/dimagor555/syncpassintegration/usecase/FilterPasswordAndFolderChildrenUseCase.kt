package ru.dimagor555.syncpassintegration.usecase

import ru.dimagor555.password.domain.folder.FolderChildren
import ru.dimagor555.password.domain.folder.toChildId
import ru.dimagor555.password.domain.password.Password
import ru.dimagor555.password.usecase.password.repository.PasswordRepository
import ru.dimagor555.password.usecase.passwordsandfolderchildren.GetPasswordsAndFolderChildrenUsecase
import ru.dimagor555.password.usecase.passwordsandfolderchildren.model.PasswordsAndFolderChildren

class FilterPasswordAndFolderChildrenUseCase(
    private val getPasswordsAndFolderChildren: GetPasswordsAndFolderChildrenUsecase,
    private val passwordRepository: PasswordRepository,
) {

    suspend operator fun invoke(passwordsIds: List<String>): PasswordsAndFolderChildren? {
        val passwordsAndFolderChildren = getPasswordsAndFolderChildren() ?: return null
        val filteredPasswords = getPasswordsByIds(passwordsIds)
        val filteredFolderChildren =
            filterFolderChildren(filteredPasswords, passwordsAndFolderChildren.folderChildren)
        return passwordsAndFolderChildren.copy(
            passwords = filteredPasswords,
            folderChildren = filteredFolderChildren,
        )
    }

    private fun filterFolderChildren(
        filteredPasswords: List<Password>,
        folderChildren: List<FolderChildren>,
    ): List<FolderChildren> {
        val childIds = filteredPasswords.map { it.toChildId() }
        val filteredFolderChildren = folderChildren.filter {
            it.childrenIds.any { childId ->
                childId in childIds
            }
        }
        return filteredFolderChildren
    }

    //TODO replace by 1 function in passwordRepository
    private suspend fun getPasswordsByIds(ids: List<String>): List<Password> =
        ids.mapNotNull { passwordRepository.getById(it) }
}