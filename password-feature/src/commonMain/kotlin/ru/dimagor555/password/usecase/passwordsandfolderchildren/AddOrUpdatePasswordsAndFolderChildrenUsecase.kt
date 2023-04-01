package ru.dimagor555.password.usecase.passwordsandfolderchildren

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext
import ru.dimagor555.password.usecase.passwordsandfolderchildren.model.PasswordsAndFolderChildren
import ru.dimagor555.password.usecase.passwordsandfolderchildren.model.partitionToAddAndToUpdate
import ru.dimagor555.password.usecase.passwordsandfolderchildren.repository.BulkPasswordRepository
import ru.dimagor555.password.usecase.passwordsandfolderchildren.UpdatePasswordsAndFolderChildrenUsecase.Result as UpdateResult

class AddOrUpdatePasswordsAndFolderChildrenUsecase(
    private val passwordRepository: BulkPasswordRepository,
    private val addPasswordsAndFolderChildren: AddPasswordsAndFolderChildrenUsecase,
    private val updatePasswordsAndFolderChildren: UpdatePasswordsAndFolderChildrenUsecase,
) {

    data class Params(
        val passwordsAndFolderChildren: PasswordsAndFolderChildren,
        val isForceUpdate: Boolean = false,
    )

    suspend operator fun invoke(params: Params): StatisticsResult {
        val result = MutableStatisticsResult()
        addOrUpdate(params, result)
        return result
    }

    private suspend fun addOrUpdate(
        params: Params,
        result: MutableStatisticsResult,
    ) = withContext(Dispatchers.Default) {
        val oldPasswords = passwordRepository.getAll()
        val (passwordsToAdd, passwordsToUpdate) = params
            .passwordsAndFolderChildren
            .partitionToAddAndToUpdate(oldPasswords)
        withContext(NonCancellable) {
            passwordsToAdd?.add(result)
            passwordsToUpdate?.update(params, result)
        }
    }

    private suspend fun PasswordsAndFolderChildren.add(result: MutableStatisticsResult) {
        addPasswordsAndFolderChildren(this)
            .also { result.fillAddResult(this.passwords.size) }
    }

    private fun MutableStatisticsResult.fillAddResult(addedCount: Int) {
        addedPasswordsCount = addedCount
    }

    private suspend fun PasswordsAndFolderChildren.update(
        params: Params,
        result: MutableStatisticsResult,
    ) {
        updatePasswordsAndFolderChildren(this.toUpdateParams(params))
            .also { result.fillUpdateResult(it) }
    }

    private fun MutableStatisticsResult.fillUpdateResult(updateResult: UpdateResult) {
        updatedPasswordsCount = updateResult.updatedPasswordsCount
        archivedPasswordsCount = updateResult.archivedPasswordsCount
    }

    private fun PasswordsAndFolderChildren.toUpdateParams(
        params: Params,
    ) = UpdatePasswordsAndFolderChildrenUsecase.Params(
        passwordsAndFolderChildren = this,
        isForceUpdate = params.isForceUpdate,
    )

    interface StatisticsResult {
        val addedPasswordsCount: Int
        val updatedPasswordsCount: Int
        val archivedPasswordsCount: Int
    }

    private data class MutableStatisticsResult(
        override var addedPasswordsCount: Int = 0,
        override var updatedPasswordsCount: Int = 0,
        override var archivedPasswordsCount: Int = 0,
    ) : StatisticsResult
}