package ru.dimagor555.password.usecase.passwordsandfolderchildren

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import ru.dimagor555.password.usecase.passwordsandfolderchildren.repository.BulkFolderChildrenRepository
import ru.dimagor555.password.usecase.passwordsandfolderchildren.repository.BulkPasswordRepository

class RemovePasswordsAndFolderChildrenUsecase(
    private val passwordRepository: BulkPasswordRepository,
    private val folderChildrenRepository: BulkFolderChildrenRepository,
) {

    suspend operator fun invoke(): Unit = coroutineScope {
        launch { passwordRepository.removeAll() }
        launch { folderChildrenRepository.removeAllChildrenFromAllFolders() }
    }
}