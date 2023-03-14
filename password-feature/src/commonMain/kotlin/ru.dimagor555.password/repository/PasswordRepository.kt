package ru.dimagor555.password.repository

import kotlinx.coroutines.flow.Flow
import ru.dimagor555.password.domain.password.Password

interface PasswordRepository {

    fun observeAll(): Flow<List<Password>>

    fun observeById(id: String): Flow<Password?>

    suspend fun getPasswordsByIds(ids: Set<String>): List<Password>

    suspend fun getById(id: String): Password?

    suspend fun add(password: Password): String

    suspend fun update(password: Password)

    suspend fun remove(id: String)

    suspend fun removeFolderPasswords(passwordIds: List<String>)
}

internal suspend fun PasswordRepository.getByIdOrThrowException(id: String) =
    getById(id) ?: error("Password with id=$id does not exist")
