package ru.dimagor555.encryption.domain

interface Base64 {
    fun encodeToString(input: ByteArray): String

    fun decodeFromString(input: String): ByteArray
}