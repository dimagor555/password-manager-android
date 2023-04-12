package ru.dimagor555.password.usecase.folder.repository

import kotlinx.coroutines.flow.Flow
import ru.dimagor555.password.domain.folder.Folder

interface FolderRepository {

    fun observeAll(): Flow<List<Folder>>

    suspend fun observeById(id: String): Flow<Folder?>

    suspend fun getById(id: String): Folder?

    suspend fun add(folder: Folder): String

    suspend fun update(folder: Folder)

    suspend fun remove(id: String)

    suspend fun removeFolderFolders(folderIds: List<String>)
}