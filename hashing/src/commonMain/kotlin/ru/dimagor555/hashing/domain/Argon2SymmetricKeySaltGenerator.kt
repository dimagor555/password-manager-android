package ru.dimagor555.hashing.domain

internal class Argon2SymmetricKeySaltGenerator(
    private val sha256Hasher: Sha256Hasher,
) {

    suspend fun generateFromPassword(passwordBytes: ByteArray): ByteArray =
        sha256Hasher.hash(passwordBytes)
}