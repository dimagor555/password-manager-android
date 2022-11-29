package ru.dimagor555.password.repository

import kotlinx.coroutines.flow.Flow
import ru.dimagor555.password.domain.password.Password

interface PasswordRepository {

    fun observeAll(): Flow<List<Password>>

    fun observeById(id: String): Flow<Password?>

    suspend fun getById(id: String): Password?

    suspend fun add(password: Password)

    suspend fun update(password: Password)

    suspend fun remove(passwordId: String)
}

internal suspend fun PasswordRepository.getByIdOrThrowException(passwordId: String) =
    getById(passwordId) ?: error("Password with id=$passwordId does not exist")
