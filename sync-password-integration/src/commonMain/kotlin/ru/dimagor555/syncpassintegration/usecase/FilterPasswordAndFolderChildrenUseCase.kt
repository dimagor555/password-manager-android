package ru.dimagor555.syncpassintegration.usecase

import io.github.aakira.napier.Napier
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
        Napier.e("FilterPasswordAndFolderChildrenUseCase passwordsIds = $passwordsIds")
        val passwordsAndFolderChildren = getPasswordsAndFolderChildren() ?: return null //TODO почему-то null
        Napier.e("FilterPasswordAndFolderChildrenUseCase passwordsAndFolderChildren = $passwordsAndFolderChildren")
        val filteredPasswords = getPasswordsByIds(passwordsIds)
        Napier.e("FilterPasswordAndFolderChildrenUseCase filteredPasswords = $filteredPasswords")
        val filteredFolderChildren =
            filterFolderChildren(filteredPasswords, passwordsAndFolderChildren.folderChildren)
        Napier.e("FilterPasswordAndFolderChildrenUseCase filteredFolderChildren = $filteredFolderChildren")

        val filteredPasswordsAndFolderChildren = passwordsAndFolderChildren.copy(
            passwords = filteredPasswords,
            folderChildren = filteredFolderChildren,
        )
        Napier.e("FilterPasswordAndFolderChildrenUseCase filteredPasswordsAndFolderChildren = $filteredPasswordsAndFolderChildren")
        return filteredPasswordsAndFolderChildren
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