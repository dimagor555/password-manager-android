package ru.dimagor555.password.usecase.passwordsandfolderchildren

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.dimagor555.password.usecase.passwordsandfolderchildren.model.PasswordsAndFolderChildren
import ru.dimagor555.password.usecase.passwordsandfolderchildren.model.toAddChangeFolderParams
import ru.dimagor555.password.usecase.passwordsandfolderchildren.repository.BulkFolderChildrenRepository
import ru.dimagor555.password.usecase.passwordsandfolderchildren.repository.BulkPasswordRepository

class AddPasswordsAndFolderChildrenUsecase(
    private val passwordRepository: BulkPasswordRepository,
    private val folderChildrenRepository: BulkFolderChildrenRepository,
) {

    suspend operator fun invoke(params: PasswordsAndFolderChildren): Unit =
        withContext(Dispatchers.Default + NonCancellable) {
            launch {
                passwordRepository.addAll(params.passwords)
            }
            launch {
                folderChildrenRepository.changeAllChildrenFolders(params.toAddChangeFolderParams())
            }
        }
}