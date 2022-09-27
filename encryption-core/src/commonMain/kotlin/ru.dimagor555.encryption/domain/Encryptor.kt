package ru.dimagor555.encryption.domain

interface Encryptor {
    fun encrypt(input: String): String
}