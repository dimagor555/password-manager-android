package ru.dimagor555.password.usecase.passwordsandfolderchildren

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import ru.dimagor555.password.usecase.passwordsandfolderchildren.model.PasswordsAndFolderChildren
import ru.dimagor555.password.usecase.passwordsandfolderchildren.model.partitionToAddAndToUpdate
import ru.dimagor555.password.usecase.passwordsandfolderchildren.repository.BulkPasswordRepository

// TODO consider different types of update
class AddOrUpdatePasswordsAndFolderChildrenUsecase(
    private val passwordRepository: BulkPasswordRepository,
    private val add: AddPasswordsAndFolderChildrenUsecase,
    private val update: UpdatePasswordsAndFolderChildrenUsecase,
) {

    data class Params(
        val passwordsAndFolderChildren: PasswordsAndFolderChildren,
    )

    suspend operator fun invoke(params: Params): Unit = coroutineScope {
        val oldPasswords = passwordRepository.getAll()
        val (passwordsToAdd, passwordsToUpdate) = params
            .passwordsAndFolderChildren
            .partitionToAddAndToUpdate(oldPasswords)
        launch { add(passwordsToAdd ?: return@launch) }
        launch { update(passwordsToUpdate ?: return@launch) }
    }
}