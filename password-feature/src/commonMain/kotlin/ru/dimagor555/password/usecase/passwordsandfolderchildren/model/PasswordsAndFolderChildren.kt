package ru.dimagor555.password.usecase.passwordsandfolderchildren.model

import kotlinx.serialization.Serializable
import ru.dimagor555.password.domain.folder.ChildId
import ru.dimagor555.password.domain.folder.FolderChildren
import ru.dimagor555.password.domain.folder.toChildId
import ru.dimagor555.password.domain.password.Password
import ru.dimagor555.password.domain.password.validate
import ru.dimagor555.password.usecase.folderchildren.repository.ChangeFolderParam

// TODO consider that password has many folders
@Serializable
data class PasswordsAndFolderChildren private constructor(
    val passwords: List<Password>,
    val folderChildren: List<FolderChildren>,
) {

    init {
        require(passwords.isNotEmpty()) { "passwords must not be empty" }
        require(folderChildren.isNotEmpty()) { "folderChildren must not be empty" }
        require(isAllPasswordsHasFolder()) { "all passwords must be in a folder" }
        // TODO remove validation when PasswordFields will be refactored
        require(isAllPasswordsValid()) { "all passwords must be valid" }
    }

    private fun isAllPasswordsHasFolder(): Boolean {
        val allChildIds = folderChildren
            .flatMap { it.childrenIds }
            .toSet()
        val passwordIds = passwords.map { it.toChildId() }
        return passwordIds.all { it in allChildIds }
    }

    private fun isAllPasswordsValid(): Boolean =
        passwords.all { it.fields.validate().isEmpty() }

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
            runCatching {
                PasswordsAndFolderChildren(
                    passwords = passwords,
                    folderChildren = folderChildren,
                )
            }.getOrNull()
    }
}

internal fun PasswordsAndFolderChildren.toAddChangeFolderParams(): List<ChangeFolderParam> =
    passwords
        .map { it.toChildId() }
        .flatMap { childId ->
            createAddChangeFolderParamsForChild(folderChildren, childId)
        }

private fun createAddChangeFolderParamsForChild(
    folderChildren: List<FolderChildren>,
    childId: ChildId,
): List<ChangeFolderParam> =
    folderChildren
        .parentIdsOfChild(childId)
        .map { parentId ->
            ChangeFolderParam.Add(
                childId = childId,
                toParentId = parentId,
            )
        }

private fun List<FolderChildren>.parentIdsOfChild(childId: ChildId): List<String> =
    this
        .filter { childId in it.childrenIds }
        .map { it.parentId }

internal fun PasswordsAndFolderChildren.toUpdateChangeFolderParams(
    oldFolderChildren: List<FolderChildren>,
): List<ChangeFolderParam> =
    passwords
        .map { it.toChildId() }
        .flatMap { childId ->
            val oldParentIds = oldFolderChildren.parentIdsOfChild(childId)
            val newParentIds = folderChildren.parentIdsOfChild(childId)
            childId.toUpdateChangeFolderParams(
                oldParentIds = oldParentIds,
                newParentIds = newParentIds,
            )
        }

private fun ChildId.toUpdateChangeFolderParams(
    oldParentIds: List<String>,
    newParentIds: List<String>,
): List<ChangeFolderParam> {
    val childId = this
    val parentIdsToAdd = newParentIds.filter { it !in oldParentIds }
    val parentIdsToRemove = oldParentIds.filter { it !in newParentIds }
    return createAddChangeFolderParams(parentIdsToAdd, childId) +
            createRemoveChangeFolderParams(parentIdsToRemove, childId)
}

private fun createAddChangeFolderParams(
    parentIds: List<String>,
    childId: ChildId,
): List<ChangeFolderParam> =
    parentIds.map { parentId ->
        ChangeFolderParam.Add(
            childId = childId,
            toParentId = parentId,
        )
    }

private fun createRemoveChangeFolderParams(
    parentIds: List<String>,
    childId: ChildId,
): List<ChangeFolderParam> =
    parentIds.map { parentId ->
        ChangeFolderParam.Remove(
            childId = childId,
            fromParentId = parentId,
        )
    }

internal fun PasswordsAndFolderChildren.partitionToAddAndToUpdate(
    oldPasswords: List<Password>,
): Pair<PasswordsAndFolderChildren?, PasswordsAndFolderChildren?> {
    val oldPasswordIds = oldPasswords.map { it.id!! }
    val (passwordsToUpdate, passwordsToAdd) = passwords.partition { it.id in oldPasswordIds }
    return this.copyOrNull(passwords = passwordsToAdd) to
            this.copyOrNull(passwords = passwordsToUpdate)
}