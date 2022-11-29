package ru.dimagor555.password.usecase.folder

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dimagor555.password.domain.Child
import ru.dimagor555.password.domain.folder.Folder
import ru.dimagor555.password.domain.password.field.ShortTextField
import ru.dimagor555.password.repository.FolderRepository

class CreateFolderUseCase(
    private val folderRepository: FolderRepository,
) {

    data class Params(
        val parentId: String,
        val title: ShortTextField,
        val children: Set<Child>,
    )

    suspend operator fun invoke(params: Params) = withContext(Dispatchers.Default) {
        val folder = createFolder(params)
        folderRepository.add(folder)
    }

    private fun createFolder(params: Params) = Folder(
        parentId = params.parentId,
        title = params.title,
        children = params.children,
    )
}