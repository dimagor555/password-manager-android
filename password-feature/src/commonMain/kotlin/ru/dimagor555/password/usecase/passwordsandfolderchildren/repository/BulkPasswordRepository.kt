package ru.dimagor555.password.usecase.passwordsandfolderchildren.repository

import ru.dimagor555.password.domain.password.Password

interface BulkPasswordRepository {

    suspend fun getAll(): List<Password>

    suspend fun addAll(passwords: List<Password>)

    suspend fun updateAll(passwords: List<Password>)

    suspend fun removeAll()
}