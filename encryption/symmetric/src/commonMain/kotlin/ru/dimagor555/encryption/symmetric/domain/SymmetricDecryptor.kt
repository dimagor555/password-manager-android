package ru.dimagor555.encryption.symmetric.domain

interface SymmetricDecryptor {

    fun decrypt(ciphertext: String, customKey: SymmetricKey? = null): String
}