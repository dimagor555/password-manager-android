package ru.dimagor555.hashing.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dimagor555.hashing.domain.Sha256Hasher
import java.security.MessageDigest

class Sha256HasherImpl : Sha256Hasher {

    override suspend fun hash(bytes: ByteArray): ByteArray = withContext(Dispatchers.Default) {
        val messageDigest = MessageDigest.getInstance("SHA-256")
        messageDigest.digest(bytes)
    }
}