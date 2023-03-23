package ru.dimagor555.password.usecase.passwordsandfolderchildren

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import ru.dimagor555.password.usecase.passwordsandfolderchildren.model.PasswordsAndFolderChildren
import ru.dimagor555.password.usecase.passwordsandfolderchildren.model.toFolderChildParams
import ru.dimagor555.password.usecase.passwordsandfolderchildren.repository.BulkFolderChildrenRepository
import ru.dimagor555.password.usecase.passwordsandfolderchildren.repository.BulkPasswordRepository

class AddPasswordsAndFolderChildrenUsecase(
    private val passwordRepository: BulkPasswordRepository,
    private val folderChildrenRepository: BulkFolderChildrenRepository,
) {

    suspend operator fun invoke(params: PasswordsAndFolderChildren) = coroutineScope {
        launch { passwordRepository.addAll(params.passwords) }
        launch { folderChildrenRepository.addAllChildrenToFolders(params.toFolderChildParams()) }
    }
}