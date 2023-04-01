package ru.dimagor555.password.usecase.passwordsandfolderchildren.repository

import ru.dimagor555.password.domain.folder.FolderChildren
import ru.dimagor555.password.usecase.folderchildren.repository.ChangeFolderParam

interface BulkFolderChildrenRepository {

    suspend fun getAll(): List<FolderChildren>

    suspend fun changeAllChildrenFolders(params: List<ChangeFolderParam>)

    suspend fun removeAllChildrenFromAllFolders()
}