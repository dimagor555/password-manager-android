package ru.dimagor555.password.usecase.passwordsandfolderchildren

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import ru.dimagor555.password.usecase.passwordsandfolderchildren.model.PasswordsAndFolderChildren
import ru.dimagor555.password.usecase.passwordsandfolderchildren.repository.BulkFolderChildrenRepository
import ru.dimagor555.password.usecase.passwordsandfolderchildren.repository.BulkPasswordRepository

class GetPasswordsAndFolderChildrenUsecase(
    private val passwordRepository: BulkPasswordRepository,
    private val folderChildrenRepository: BulkFolderChildrenRepository,
) {

    suspend operator fun invoke(): PasswordsAndFolderChildren? = coroutineScope {
        val passwordsDeferred = async { passwordRepository.getAll() }
        val folderChildrenDeferred = async { folderChildrenRepository.getAll() }
        PasswordsAndFolderChildren.createOrNull(
            passwords = passwordsDeferred.await(),
            folderChildren = folderChildrenDeferred.await(),
        )
    }
}