package ru.dimagor555.export.usecase.repository

import kotlinx.serialization.json.JsonObject
import ru.dimagor555.export.domain.Export

interface PasswordsAndFolderChildrenRepository {

    suspend fun getOrNull(): JsonObject?

    suspend fun import(export: Export)

    suspend fun importWithClearing(export: Export)
}