package ru.dimagor555.encryption.symmetric.domain

interface SymmetricEncryptor {
    fun encrypt(input: String): String
}