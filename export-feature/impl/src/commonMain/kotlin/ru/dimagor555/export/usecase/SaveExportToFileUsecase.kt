package ru.dimagor555.export.usecase

import ru.dimagor555.encryption.symmetric.domain.SymmetricEncryptor
import ru.dimagor555.export.domain.Export
import ru.dimagor555.export.domain.encrypted
import ru.dimagor555.export.usecase.repository.StorageRepository
import ru.dimagor555.export.domain.SaveExportToFileResult as Result

internal class SaveExportToFileUsecase(
    private val collectExport: CollectExportUsecase,
    private val storageRepository: StorageRepository,
    private val encryptor: SymmetricEncryptor,
) {

    data class Params(
        val fileUri: String,
    )

    suspend operator fun invoke(params: Params): Result {
        val export = collectExportOrElse { return Result.NoPasswords }
        saveExportToFileOrElse(
            fileUri = params.fileUri,
            export = export,
            onError = { return Result.ExportError },
        )
        return Result.Success
    }

    private suspend inline fun collectExportOrElse(onError: () -> Nothing): Export =
        collectExport() ?: onError()

    private suspend inline fun saveExportToFileOrElse(
        fileUri: String,
        export: Export,
        onError: () -> Nothing,
    ) = runCatching {
        val encryptedExport = export.encrypted(encryptor)
        storageRepository.writeToFile(fileUri, encryptedExport)
    }.onFailure { onError() }
}