package ru.dimagor555.encryption.core

import java.security.MessageDigest

class SHA256Hasher {
    fun getSHA(password: String): ByteArray {
        val md = MessageDigest.getInstance("SHA-256")
        return md.digest(password.toByteArray())
    }
}
