package ru.dimagor555.export.usecase

import io.github.aakira.napier.Napier
import ru.dimagor555.export.domain.Export
import ru.dimagor555.export.usecase.repository.PasswordsAndFolderChildrenRepository
import kotlin.Boolean
import kotlin.Nothing
import kotlin.String
import kotlin.getOrElse
import kotlin.onFailure
import kotlin.runCatching
import ru.dimagor555.export.usecase.ReadExportFromFileUsecase.Result as ReadResult

// TODO add backups before import
internal class LoadExportFromFileUsecase(
    private val readExportFromFile: ReadExportFromFileUsecase,
    private val passwordsAndFolderChildrenRepository: PasswordsAndFolderChildrenRepository,
) {

    data class Params(
        val fileUri: String,
        val isClearBeforeImport: Boolean,
        val isMakeBackup: Boolean,
    )

    suspend operator fun invoke(params: Params): Result {
        val export = readExportFromFileOrElse(params.fileUri) { error -> return error }
        importPasswordsOrElse(export, params) { return Result.ImportError }
        return Result.Success
    }

    private suspend inline fun readExportFromFileOrElse(
        fileUri: String,
        onError: (Result) -> Nothing,
    ): Export {
        val result = readExportFromFile(
            ReadExportFromFileUsecase.Params(fileUri = fileUri),
        )
        when (result) {
            is ReadResult.Success -> return result.export
            is ReadResult.CouldNotReadFile -> onError(Result.CouldNotReadFile)
            is ReadResult.InvalidFileFormat -> onError(Result.InvalidFileFormat)
        }
    }

    private suspend inline fun importPasswordsOrElse(
        export: Export,
        params: Params,
        onError: () -> Nothing,
    ) = runCatching {
        if (params.isClearBeforeImport) {
            passwordsAndFolderChildrenRepository.importWithClearing(export)
        } else {
            passwordsAndFolderChildrenRepository.import(export)
        }
    }
        .onFailure { Napier.e("error during import", it) }
        .getOrElse { onError() }

    sealed interface Result {

        object Success : Result

        object CouldNotReadFile : Result

        object InvalidFileFormat : Result

        object ImportError : Result
    }
}