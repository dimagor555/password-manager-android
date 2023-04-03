package ru.dimagor555.export.usecase

import io.github.aakira.napier.Napier
import ru.dimagor555.encryption.domain.Decryptor
import ru.dimagor555.export.domain.EncryptedExport
import ru.dimagor555.export.domain.Export
import ru.dimagor555.export.domain.decrypted
import ru.dimagor555.export.usecase.repository.StorageRepository

internal class ReadExportFromFileUsecase(
    private val storageRepository: StorageRepository,
    private val decryptor: Decryptor,
) {

    data class Params(
        val fileUri: String,
    )

    suspend operator fun invoke(params: Params): Result {
        val encryptedExport = readExportFromFileOrElse(params.fileUri) {
            return Result.CouldNotReadFile
        }
        val export = decryptExportOrElse(encryptedExport) {
            return Result.InvalidFileFormat
        }
        return Result.Success(export)
    }

    private suspend inline fun readExportFromFileOrElse(
        fileUri: String,
        onError: () -> Nothing,
    ): EncryptedExport =
        runCatching { storageRepository.readFromFile(fileUri) }
            .onFailure { Napier.e("could not read export file", it) }
            .getOrElse { onError() }

    private suspend inline fun decryptExportOrElse(
        encryptedExport: EncryptedExport,
        onError: () -> Nothing,
    ): Export =
        runCatching { encryptedExport.decrypted(decryptor) }
            .onFailure { Napier.e("could not decrypt export", it) }
            .getOrElse { onError() }

    sealed interface Result {

        data class Success(val export: Export) : Result

        object CouldNotReadFile : Result

        object InvalidFileFormat : Result
    }
}