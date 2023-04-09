package ru.dimagor555.hashing.data

import ru.dimagor555.hashing.domain.Argon2Params
import java.security.SecureRandom

internal class Argon2SecureRandomSaltGenerator {

    fun generate(): ByteArray {
        val salt = ByteArray(Argon2Params.SALT_LENGTH_BYTES)
        SecureRandom().apply { nextBytes(salt) }
        return salt
    }
}