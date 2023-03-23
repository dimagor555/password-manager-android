package ru.dimagor555.password.usecase.passwordsandfolderchildren

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import ru.dimagor555.password.usecase.passwordsandfolderchildren.model.PasswordsAndFolderChildren
import ru.dimagor555.password.usecase.passwordsandfolderchildren.model.toChangeFolderParams
import ru.dimagor555.password.usecase.passwordsandfolderchildren.repository.BulkFolderChildrenRepository
import ru.dimagor555.password.usecase.passwordsandfolderchildren.repository.BulkPasswordRepository

class UpdatePasswordsAndFolderChildrenUsecase(
    private val passwordRepository: BulkPasswordRepository,
    private val folderChildrenRepository: BulkFolderChildrenRepository,
) {

    suspend operator fun invoke(params: PasswordsAndFolderChildren) = coroutineScope {
        launch { passwordRepository.updateAll(params.passwords) }
        launch {
            val oldFolderChildren = folderChildrenRepository.getAll()
            val changeFolderParams = params
                .toChangeFolderParams(oldFolderChildren)
                .ifEmpty { null }
                ?: return@launch
            folderChildrenRepository.changeAllChildrenFolders(changeFolderParams)
        }
    }
}