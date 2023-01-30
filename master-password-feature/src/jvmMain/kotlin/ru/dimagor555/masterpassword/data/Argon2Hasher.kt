package ru.dimagor555.masterpassword.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder
import ru.dimagor555.masterpassword.domain.Hasher

class Argon2Hasher : Hasher {

    override suspend fun hash(password: String): String =
        withContext(Dispatchers.Default) {
            Argon2PasswordEncoder().encode(password)
        }

    override suspend fun verify(passwordHash: String, passwordToVerify: String): Boolean =
        withContext(Dispatchers.Default) {
            Argon2PasswordEncoder().matches(passwordToVerify, passwordHash)
        }
}