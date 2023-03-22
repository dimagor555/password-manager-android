package ru.dimagor555.password.repository

import kotlinx.coroutines.flow.Flow
import ru.dimagor555.password.domain.Child
import ru.dimagor555.password.domain.folder.ChildId
import ru.dimagor555.password.domain.folder.FolderChildren

interface FolderChildrenRepository {

    fun getById(id: String): FolderChildren?

    suspend fun observeById(parentId: String): Flow<FolderChildren?>

    fun getChildObjects(id: String): Set<Child>

    fun getParentId(id: String): String

    fun getFolderPasswords(id: String): List<String>

    fun getFolderFolders(id: String): List<String>

    suspend fun add(folderChildrenModel: FolderChildren)

    suspend fun update(folderChildren: FolderChildren)

    suspend fun changeChildFolder(params: ChangeFolderParams)

    suspend fun <T : ChildId> addChildToFolder(parentId: String, childId: T)

    suspend fun <T : ChildId> removeChildFromFolder(parentId: String, childId: T)

    suspend fun removeAllChildrenFromAllFolders()

    suspend fun remove(id: String)
}

data class ChangeFolderParams(
    val childId: ChildId,
    val fromParentId: String,
    val toParentId: String,
)