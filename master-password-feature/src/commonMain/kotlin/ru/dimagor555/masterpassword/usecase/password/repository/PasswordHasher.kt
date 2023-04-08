package ru.dimagor555.masterpassword.usecase.password.repository

internal interface PasswordHasher {

    suspend fun hash(password: String): String

    suspend fun verify(passwordHash: String, passwordToVerify: String): Boolean
}