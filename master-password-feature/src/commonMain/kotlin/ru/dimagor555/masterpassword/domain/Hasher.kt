package ru.dimagor555.masterpassword.domain

interface Hasher {

    suspend fun hash(password: String): String

    suspend fun verify(passwordHash: String, passwordToVerify: String): Boolean
}