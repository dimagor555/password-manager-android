package ru.dimagor555.password.domain.folder

import kotlinx.serialization.Serializable
import ru.dimagor555.password.domain.password.Password

@Serializable
data class FolderChildren(
    val parentId: String = "",
    val childrenIds: Set<ChildId> = setOf(),
)

fun Folder.toFolderChildren(parentId: String) = FolderChildren(
    parentId = parentId,
    childrenIds = children.map {
        when(it) {
            is Folder -> ChildId.FolderId(it.id!!)
            is Password -> ChildId.PasswordId(it.id!!)
            else -> error("unknown child type")
        }
    }.toSet()
)
