package ru.dimagor555.encryption.symmetric.domain

interface SymmetricDecryptor {
    fun decrypt(input: String): String
}