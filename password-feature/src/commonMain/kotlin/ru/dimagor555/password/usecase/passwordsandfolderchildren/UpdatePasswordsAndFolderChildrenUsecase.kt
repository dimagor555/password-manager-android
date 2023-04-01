package ru.dimagor555.password.usecase.passwordsandfolderchildren

import kotlinx.coroutines.*
import kotlinx.datetime.Clock
import ru.dimagor555.password.domain.folder.Folder
import ru.dimagor555.password.domain.folder.passwordIdOrNull
import ru.dimagor555.password.domain.folder.toChildId
import ru.dimagor555.password.domain.password.Password
import ru.dimagor555.password.usecase.folderchildren.repository.ChangeFolderParam
import ru.dimagor555.password.usecase.passwordsandfolderchildren.model.PasswordsAndFolderChildren
import ru.dimagor555.password.usecase.passwordsandfolderchildren.model.toUpdateChangeFolderParams
import ru.dimagor555.password.usecase.passwordsandfolderchildren.repository.BulkFolderChildrenRepository
import ru.dimagor555.password.usecase.passwordsandfolderchildren.repository.BulkPasswordRepository

class UpdatePasswordsAndFolderChildrenUsecase(
    private val passwordRepository: BulkPasswordRepository,
    private val folderChildrenRepository: BulkFolderChildrenRepository,
) {

    data class Params(
        val passwordsAndFolderChildren: PasswordsAndFolderChildren,
        val isForceUpdate: Boolean,
    )

    suspend operator fun invoke(params: Params): Result =
        withContext(Dispatchers.Default) {
            val changeFolderParams = computeChangeFolderParams(params.passwordsAndFolderChildren)
            val passwordsToUpdate = computePasswordsToUpdate(params, changeFolderParams)
            updatePasswordsAndFolderChildren(
                passwords = passwordsToUpdate,
                changeFolderParams = changeFolderParams,
            )
            computeResult(
                updatedPasswords = passwordsToUpdate,
                changeFolderParams = changeFolderParams,
            )
        }

    private suspend fun computeChangeFolderParams(
        passwordsAndFolderChildren: PasswordsAndFolderChildren,
    ): List<ChangeFolderParam> {
        val oldFolderChildren = folderChildrenRepository.getAll()
        return passwordsAndFolderChildren.toUpdateChangeFolderParams(oldFolderChildren)
    }

    private suspend fun computePasswordsToUpdate(
        params: Params,
        changeFolderParams: List<ChangeFolderParam>,
    ): List<Password> {
        val passwords = params.passwordsAndFolderChildren.passwords
        val oldPasswordsByIds = getOldPasswords().associateBy { it.id!! }
        val actuallyUpdatedPasswords = passwords.filterActuallyUpdatedPasswords(
            oldPasswordsByIds = oldPasswordsByIds,
            changeFolderParams = changeFolderParams,
        )
        return if (params.isForceUpdate) {
            actuallyUpdatedPasswords.updatedNow()
        } else {
            actuallyUpdatedPasswords.filterEditedInFuture(oldPasswordsByIds)
        }
    }

    private suspend fun getOldPasswords(): List<Password> =
        passwordRepository.getAll()

    private fun List<Password>.filterActuallyUpdatedPasswords(
        oldPasswordsByIds: Map<String, Password>,
        changeFolderParams: List<ChangeFolderParam>,
    ): List<Password> {
        val changedFolderPasswordIds = changeFolderParams.toPasswordIds()
        return this.filter { newPassword ->
            newPassword.isActuallyUpdated(
                oldPasswordsByIds = oldPasswordsByIds,
                changedFolderPasswordIds = changedFolderPasswordIds,
            )
        }
    }

    private fun List<ChangeFolderParam>.toPasswordIds(): List<String> =
        this.mapNotNull { it.childId.passwordIdOrNull }

    private fun Password.isActuallyUpdated(
        oldPasswordsByIds: Map<String, Password>,
        changedFolderPasswordIds: List<String>,
    ): Boolean {
        val oldPassword = oldPasswordsByIds[id]!!
        val isChangedFolder = id in changedFolderPasswordIds
        return this != oldPassword || isChangedFolder
    }

    private fun List<Password>.updatedNow(): List<Password> {
        val now = Clock.System.now()
        return this.map {
            it.copy(metadata = it.metadata.copy(editingDateTime = now))
        }
    }

    private fun List<Password>.filterEditedInFuture(
        oldPasswordsByIds: Map<String, Password>,
    ): List<Password> =
        this.filter {
            val oldPassword = oldPasswordsByIds[it.id]!!
            it.metadata.editingDateTime > oldPassword.metadata.editingDateTime
        }

    private suspend fun updatePasswordsAndFolderChildren(
        passwords: List<Password>,
        changeFolderParams: List<ChangeFolderParam>,
    ) = withContext(NonCancellable) {
        updatePasswordsAsync(passwords)
        if (changeFolderParams.isNotEmpty()) {
            updateFolderChildrenAsync(changeFolderParams)
        }
    }

    private fun CoroutineScope.updatePasswordsAsync(
        passwords: List<Password>,
    ) = launch {
        passwordRepository.updateAll(passwords)
    }

    private fun CoroutineScope.updateFolderChildrenAsync(
        changeFolderParams: List<ChangeFolderParam>,
    ) = launch {
        folderChildrenRepository.changeAllChildrenFolders(changeFolderParams)
    }

    private fun computeResult(
        updatedPasswords: List<Password>,
        changeFolderParams: List<ChangeFolderParam>,
    ): Result {
        val archivedCount = computeArchivedPasswordsCount(updatedPasswords, changeFolderParams)
        return Result(
            updatedPasswordsCount = updatedPasswords.size - archivedCount,
            archivedPasswordsCount = archivedCount,
        )
    }

    private fun computeArchivedPasswordsCount(
        updatedPasswords: List<Password>,
        changeFolderParams: List<ChangeFolderParam>,
    ): Int {
        val updatedChildIds = updatedPasswords.map(Password::toChildId)
        return changeFolderParams
            .filter { it.childId in updatedChildIds }
            .filter { it.toParentId == Folder.ARCHIVE_FOLDER_ID }
            .distinctBy { it.childId }
            .count()
    }

    data class Result(
        val updatedPasswordsCount: Int,
        val archivedPasswordsCount: Int,
    ) {

        init {
            require(updatedPasswordsCount >= 0) {
                "updatedPasswordsCount could not be less than 0"
            }
            require(archivedPasswordsCount >= 0) {
                "archivedPasswordsCount could not be less than 0"
            }
        }
    }
}