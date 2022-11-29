package ru.dimagor555.password.usecase.folder

import ru.dimagor555.password.repository.FolderRepository

class RemoveFolderUseCase(
    private val folderRepository: FolderRepository
) {
    suspend operator fun invoke(folderId: String) {
        folderRepository.remove(folderId)
    }
}