package ru.dimagor555.export.data

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.core.net.toUri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dimagor555.export.domain.EncryptedExport
import ru.dimagor555.export.usecase.repository.StorageRepository

internal class StorageRepositoryImpl(
    private val context: Context,
) : StorageRepository {

    override suspend fun readFromFile(fileUri: String): EncryptedExport =
        withContext(Dispatchers.IO) {
            val fileText = context
                .contentResolver
                .openInputStream(fileUri.toUri())!!
                .use { it.bufferedReader().readText() }
            EncryptedExport(encryptedString = fileText)
        }

    override suspend fun writeToFile(fileUri: String, export: EncryptedExport): Unit =
        withContext(Dispatchers.IO) {
            context
                .contentResolver
                .openOutputStream(fileUri.toUri())
                ?.use { outputStream ->
                    outputStream
                        .bufferedWriter()
                        .apply {
                            write(export.encryptedString)
                            flush()
                        }
                }
        }

    override suspend fun getFilePathOrName(fileUri: String): String =
        withContext(Dispatchers.IO) {
            getContentFileNameOrNull(fileUri.toUri()) ?: fileUri
        }

    private fun getContentFileNameOrNull(uri: Uri): String? = runCatching {
        context
            .contentResolver
            .query(uri, null, null, null, null)
            ?.use { cursor ->
                cursor.moveToFirst()
                cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME)
                    .let(cursor::getString)
            }
    }.getOrNull()
}