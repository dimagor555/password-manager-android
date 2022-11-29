package ru.dimagor555.password.usecase.folder

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import ru.dimagor555.password.domain.Child
import ru.dimagor555.password.domain.folder.Folder
import ru.dimagor555.password.domain.password.field.FieldValidator
import ru.dimagor555.password.domain.password.field.ShortTextField
import ru.dimagor555.password.repository.FolderRepository
import ru.dimagor555.password.usecase.folder.UpdateFolderUseCase.Result.Error
import ru.dimagor555.password.usecase.folder.UpdateFolderUseCase.Result.Success
import ru.dimagor555.password.validation.core.TextValidationError

class UpdateFolderUseCase(
    private val folderRepository: FolderRepository,
) {

    data class Params(
        val id: String,
        val title: ShortTextField,
        val children: Set<Child>,
    )

    suspend operator fun invoke(params: Params): Result =
        withContext(Dispatchers.Default) {
            validate(params)
            updateFolder(params)
        }

    private fun validate(params: Params): Result = with(params) {
        Result.ValidationError(FieldValidator.getValidatorByFieldType(title).validate(title))
    }

    private suspend fun updateFolder(params: Params): Result =
        when (val oldFolder = folderRepository.getById(params.id)) {
            null -> Error
            else -> {
                val newFolder = oldFolder.createUpdated(params)
                folderRepository.update(newFolder)
                Success
            }
        }

    private fun Folder.createUpdated(params: Params) = copy(
        title = params.title,
        children = params.children,
        metadata = metadata.copy(editingDateTime = Clock.System.now()),
    )

    sealed interface Result {

        object Success : Result

        object Error : Result

        data class ValidationError(val errors: List<TextValidationError>) : Result
    }
}