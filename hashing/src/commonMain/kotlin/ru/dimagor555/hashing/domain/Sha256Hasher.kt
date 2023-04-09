package ru.dimagor555.hashing.domain

interface Sha256Hasher {

    suspend fun hash(bytes: ByteArray): ByteArray
}