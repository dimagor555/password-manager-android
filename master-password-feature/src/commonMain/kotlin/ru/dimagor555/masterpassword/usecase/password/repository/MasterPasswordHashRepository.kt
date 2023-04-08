package ru.dimagor555.masterpassword.usecase.password.repository

// TODO make internal
interface MasterPasswordHashRepository {

    suspend fun hasPasswordHash(): Boolean

    suspend fun getPasswordHash(): String

    suspend fun setPasswordHash(passwordHash: String)
}