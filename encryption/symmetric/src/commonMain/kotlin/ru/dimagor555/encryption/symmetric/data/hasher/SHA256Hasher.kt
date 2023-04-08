package ru.dimagor555.encryption.symmetric.data.hasher

import java.security.MessageDigest

internal class SHA256Hasher {

    fun hash(value: String): ByteArray {
        val messageDigest = MessageDigest.getInstance("SHA-256")
        return messageDigest.digest(value.toByteArray())
    }
}
