package ru.dimagor555.export.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dimagor555.export.domain.EncryptedExport
import ru.dimagor555.export.usecase.repository.StorageRepository
import java.io.File

internal class StorageRepositoryImpl : StorageRepository {

    override suspend fun readFromFile(fileUri: String): EncryptedExport =
        withContext(Dispatchers.IO) {
            val fileText = File(fileUri).readText()
            EncryptedExport(encryptedString = fileText)
        }

    override suspend fun writeToFile(fileUri: String, export: EncryptedExport): Unit =
        withContext(Dispatchers.IO) {
            File(fileUri).writeText(export.encryptedString)
        }
}