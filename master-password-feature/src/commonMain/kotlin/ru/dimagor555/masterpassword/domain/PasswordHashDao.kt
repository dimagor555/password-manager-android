package ru.dimagor555.masterpassword.domain

interface PasswordHashDao {

    suspend fun hasPasswordHash(): Boolean

    suspend fun getPasswordHash(): String

    suspend fun setPasswordHash(passwordHash: String)
}