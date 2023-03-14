package ru.dimagor555.password.usecase.folder

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dimagor555.password.domain.Child
import ru.dimagor555.password.domain.folder.ChildId
import ru.dimagor555.password.domain.folder.Folder
import ru.dimagor555.password.domain.folder.toFolderChildren
import ru.dimagor555.password.domain.password.field.ShortTextField
import ru.dimagor555.password.repository.FolderChildrenRepository
import ru.dimagor555.password.repository.FolderRepository

class CreateFolderUseCase(
    private val folderRepository: FolderRepository,
    private val folderChildrenRepository: FolderChildrenRepository,
) {

    data class Params(
        val id: String? = null,
        val parentId: String,
        val title: ShortTextField,
        val children: Set<Child>,
    )

    suspend operator fun invoke(params: Params) = withContext(Dispatchers.Default) {
        val folder = createFolder(params)
        val id = folderRepository.add(folder)
        folderChildrenRepository.add(folder.toFolderChildren(id))
        if (params.id == null) {
            folderChildrenRepository.addChildToFolderChildren(params.parentId, ChildId.FolderId(id))
        }
    }

    private fun createFolder(params: Params) = Folder(
        id = params.id,
        title = params.title,
        children = params.children,
    )
}
