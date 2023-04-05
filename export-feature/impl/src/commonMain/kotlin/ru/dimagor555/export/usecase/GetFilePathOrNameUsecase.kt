package ru.dimagor555.export.usecase

import ru.dimagor555.export.usecase.repository.StorageRepository

internal class GetFilePathOrNameUsecase(
    private val repository: StorageRepository,
) {

    suspend operator fun invoke(fileUri: String): String =
        repository.getFilePathOrName(fileUri)
}