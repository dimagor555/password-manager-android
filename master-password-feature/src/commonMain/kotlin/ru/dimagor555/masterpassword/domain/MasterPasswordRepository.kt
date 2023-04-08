package ru.dimagor555.masterpassword.domain

interface MasterPasswordRepository {

    suspend fun hasPassword(): Boolean

    suspend fun setPassword(password: String)

    suspend fun verifyPassword(password: String): Boolean
}