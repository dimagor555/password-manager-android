package ru.dimagor555.password.usecase.passwordsandfolderchildren

import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.dimagor555.password.usecase.passwordsandfolderchildren.repository.BulkFolderChildrenRepository
import ru.dimagor555.password.usecase.passwordsandfolderchildren.repository.BulkPasswordRepository

class RemovePasswordsAndFolderChildrenUsecase(
    private val passwordRepository: BulkPasswordRepository,
    private val folderChildrenRepository: BulkFolderChildrenRepository,
) {

    suspend operator fun invoke(): Unit = withContext(NonCancellable) {
        launch { passwordRepository.removeAll() }
        launch { folderChildrenRepository.removeAllChildrenFromAllFolders() }
    }
}