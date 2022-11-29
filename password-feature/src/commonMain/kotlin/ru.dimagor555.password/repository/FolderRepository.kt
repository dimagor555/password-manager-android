package ru.dimagor555.password.repository

import kotlinx.coroutines.flow.Flow
import ru.dimagor555.password.domain.folder.Folder

interface FolderRepository {

    fun observeAll(): Flow<List<Folder>>

    fun observeById(id: String): Flow<Folder?>

    suspend fun getById(id: String): Folder?

    suspend fun add(item: Folder)

    suspend fun update(item: Folder)

    suspend fun remove(itemId: String)
}

internal suspend fun FolderRepository.getByIdOrThrowException(folderId: String) =
    getById(folderId) ?: error("Password with id=$folderId does not exist")
