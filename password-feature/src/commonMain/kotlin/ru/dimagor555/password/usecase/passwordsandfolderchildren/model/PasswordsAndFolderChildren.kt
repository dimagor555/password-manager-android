package ru.dimagor555.password.usecase.passwordsandfolderchildren.model

import kotlinx.serialization.Serializable
import ru.dimagor555.password.domain.folder.ChildId
import ru.dimagor555.password.domain.folder.FolderChildren
import ru.dimagor555.password.domain.folder.toChildId
import ru.dimagor555.password.domain.password.Password
import ru.dimagor555.password.usecase.folderchildren.repository.ChangeFolderParams
import ru.dimagor555.password.usecase.folderchildren.repository.FolderChildParams

@Serializable
data class PasswordsAndFolderChildren private constructor(
    val passwords: List<Password>,
    val folderChildren: List<FolderChildren>,
) {

    init {
        require(passwords.isNotEmpty()) { "passwords must not be empty" }
        require(folderChildren.isNotEmpty()) { "folderChildren must not be empty" }
        require(isAllPasswordsHasFolder()) { "all passwords must be in a folder" }
    }

    private fun isAllPasswordsHasFolder(): Boolean {
        val allChildIds = folderChildren
            .flatMap { it.childrenIds }
            .toSet()
        val passwordIds = passwords.map { it.toChildId() }
        return passwordIds.all { it in allChildIds }
    }

    internal fun copyOrNull(
        passwords: List<Password> = this.passwords,
        folderChildren: List<FolderChildren> = this.folderChildren,
    ) = createOrNull(
        passwords = passwords,
        folderChildren = folderChildren,
    )

    companion object {

        fun createOrNull(
            passwords: List<Password>,
            folderChildren: List<FolderChildren>,
        ): PasswordsAndFolderChildren? =
            if (passwords.isNotEmpty() && folderChildren.isNotEmpty()) {
                PasswordsAndFolderChildren(
                    passwords = passwords,
                    folderChildren = folderChildren,
                )
            } else {
                null
            }
    }
}

internal fun PasswordsAndFolderChildren.toFolderChildParams(): List<FolderChildParams> =
    passwords
        .map { it.toChildId() }
        .map { childId ->
            FolderChildParams(
                childId = childId,
                parentId = folderChildren.parentIdOfChild(childId),
            )
        }

private fun List<FolderChildren>.parentIdOfChild(childId: ChildId): String =
    this
        .first { childId in it.childrenIds }
        .parentId

internal fun PasswordsAndFolderChildren.toChangeFolderParams(
    oldFolderChildren: List<FolderChildren>,
): List<ChangeFolderParams> =
    passwords
        .map { it.toChildId() }
        .mapNotNull { childId ->
            val oldParentId = oldFolderChildren.parentIdOfChild(childId)
            val newParentId = folderChildren.parentIdOfChild(childId)
            if (oldParentId == newParentId) {
                null
            } else {
                ChangeFolderParams(
                    childId = childId,
                    fromParentId = oldParentId,
                    toParentId = newParentId,
                )
            }
        }

internal fun PasswordsAndFolderChildren.partitionToAddAndToUpdate(
    oldPasswords: List<Password>,
): Pair<PasswordsAndFolderChildren?, PasswordsAndFolderChildren?> {
    val oldPasswordIds = oldPasswords.map { it.id!! }
    val (passwordsToUpdate, passwordsToAdd) = passwords.partition { it.id in oldPasswordIds }
    return this.copyOrNull(passwords = passwordsToAdd) to
            this.copyOrNull(passwords = passwordsToUpdate)
}