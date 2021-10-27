package ru.dimagor555.password.repository

import kotlinx.coroutines.flow.Flow
import ru.dimagor555.password.domain.Password

interface PasswordRepository {
    fun getAll(): Flow<List<Password>>

    fun getById(id: Int): Flow<Password>

    suspend fun add(password: Password)

    suspend fun update(password: Password)

    suspend fun remove(password: Password)
}