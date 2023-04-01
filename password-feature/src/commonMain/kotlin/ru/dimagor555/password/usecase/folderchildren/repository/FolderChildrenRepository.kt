package ru.dimagor555.password.usecase.folderchildren.repository

import kotlinx.coroutines.flow.Flow
import ru.dimagor555.password.domain.Child
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

    suspend fun changeChildFolder(param: ChangeFolderParam)

    suspend fun remove(id: String)
}