package ru.dimagor555.password.repository

import kotlinx.coroutines.flow.Flow
import ru.dimagor555.password.domain.Password

interface PasswordRepository {
    fun observeAll(): Flow<List<Password>>

    fun observeById(id: Int): Flow<Password>

    suspend fun getById(id: Int): Password

    suspend fun add(password: Password)

    suspend fun update(password: Password)

    suspend fun remove(passwordId: Int)
}