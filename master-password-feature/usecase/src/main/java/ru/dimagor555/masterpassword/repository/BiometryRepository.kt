package ru.dimagor555.masterpassword.repository

interface BiometryRepository {
    suspend fun canLogin(): Boolean
}