package ru.dimagor555.export.usecase.repository

import ru.dimagor555.export.domain.EncryptedExport

internal interface StorageRepository {

    suspend fun readFromFile(fileUri: String): EncryptedExport

    suspend fun writeToFile(fileUri: String, export: EncryptedExport)

    suspend fun getFilePathOrName(fileUri: String): String {
        return fileUri
    }
}