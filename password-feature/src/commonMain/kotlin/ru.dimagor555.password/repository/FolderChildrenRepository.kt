package ru.dimagor555.password.repository

import ru.dimagor555.password.domain.Child
import ru.dimagor555.password.domain.folder.ChildId
import ru.dimagor555.password.domain.folder.FolderChildren

interface FolderChildrenRepository {

    suspend fun getById(id: String): FolderChildren?

    fun getChildObjects(id: String): Set<Child>

    fun getParentId(id: String): String

    fun getFolderPasswords(id: String): List<String>

    fun getFolderFolders(id: String): List<String>

    suspend fun add(folderChildrenModel: FolderChildren)

    suspend fun update(folderChildren: FolderChildren)

    suspend fun replaceChildLocation(id: String, fromId: String, toId: String)

    suspend fun <T : ChildId> removeChildFromFolderChildren(parentId: String, childId: T)

    suspend fun <T : ChildId> addChildToFolderChildren(parentId: String, childId: T)

    suspend fun remove(id: String)
}
