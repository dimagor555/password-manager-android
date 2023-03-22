package ru.dimagor555.encryption.domain

interface Decryptor {
    fun decrypt(input: String): String
}