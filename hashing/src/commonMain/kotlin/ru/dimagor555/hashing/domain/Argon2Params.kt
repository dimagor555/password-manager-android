package ru.dimagor555.hashing.domain

internal object Argon2Params {
    const val SALT_LENGTH_BYTES = 16
    const val HASH_LENGTH_BYTES = 32
    const val PARALLELISM = 4
    const val MEMORY_KIBIBYTES = 64 * 1024
    const val ITERATIONS = 7
}