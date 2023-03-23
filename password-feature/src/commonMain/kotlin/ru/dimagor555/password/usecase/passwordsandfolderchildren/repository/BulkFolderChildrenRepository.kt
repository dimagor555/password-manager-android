package ru.dimagor555.password.usecase.passwordsandfolderchildren.repository

import ru.dimagor555.password.domain.folder.FolderChildren
import ru.dimagor555.password.usecase.folderchildren.repository.ChangeFolderParams
import ru.dimagor555.password.usecase.folderchildren.repository.FolderChildParams

interface BulkFolderChildrenRepository {

    suspend fun getAll(): List<FolderChildren>

    suspend fun addAllChildrenToFolders(params: List<FolderChildParams>)

    suspend fun changeAllChildrenFolders(params: List<ChangeFolderParams>)

    suspend fun removeAllChildrenFromAllFolders()
}