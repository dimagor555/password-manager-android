package ru.dimagor555.hashing.domain

interface PasswordHasher {

    suspend fun hash(password: String): String

    suspend fun hashForSymmetricKey(password: String): ByteArray

    suspend fun verify(passwordHash: String, passwordToVerify: String): Boolean
}