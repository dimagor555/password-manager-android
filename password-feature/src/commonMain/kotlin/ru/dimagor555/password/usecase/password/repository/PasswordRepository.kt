package ru.dimagor555.password.usecase.password.repository

import kotlinx.coroutines.flow.Flow
import ru.dimagor555.password.domain.password.Password

interface PasswordRepository {

    fun observeById(id: String): Flow<Password?>

    fun observeAll(): Flow<List<Password>>

    suspend fun getById(id: String): Password?

    suspend fun getAllByIds(ids: Set<String>): List<Password>

    suspend fun getAll(): List<Password>

    suspend fun add(password: Password): String

    suspend fun addAll(passwords: List<Password>)

    suspend fun update(password: Password)

    suspend fun updateAll(passwords: List<Password>)

    suspend fun addOrUpdateAll(passwords: List<Password>)

    suspend fun removeAllByIds(passwordIds: Set<String>)

    suspend fun removeAll()
}

internal suspend fun PasswordRepository.getByIdOrThrowException(id: String) =
    getById(id) ?: error("Password with id=$id does not exist")
