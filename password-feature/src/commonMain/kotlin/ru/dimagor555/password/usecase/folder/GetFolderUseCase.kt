package ru.dimagor555.password.usecase.folder

import ru.dimagor555.password.domain.folder.Folder
import ru.dimagor555.password.usecase.folder.repository.FolderRepository

class GetFolderUseCase(
    private val folderRepository: FolderRepository,
) {
    suspend operator fun invoke(id: String): Folder? = folderRepository.getById(id)
}