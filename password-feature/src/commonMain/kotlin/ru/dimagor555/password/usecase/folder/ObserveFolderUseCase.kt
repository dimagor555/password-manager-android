package ru.dimagor555.password.usecase.folder

import kotlinx.coroutines.flow.Flow
import ru.dimagor555.password.domain.folder.Folder
import ru.dimagor555.password.usecase.folder.repository.FolderRepository

class ObserveFolderUseCase(
    private val folderRepository: FolderRepository,
) {
    suspend operator fun invoke(id: String): Flow<Folder?> = folderRepository.observeById(id)
}
