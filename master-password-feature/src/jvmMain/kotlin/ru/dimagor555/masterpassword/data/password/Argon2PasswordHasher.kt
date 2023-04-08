package ru.dimagor555.masterpassword.data.password

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder
import ru.dimagor555.masterpassword.usecase.password.repository.PasswordHasher

internal class Argon2PasswordHasher : PasswordHasher {

    override suspend fun hash(password: String): String =
        withContext(Dispatchers.Default) {
            Argon2PasswordEncoder().encode(password)
        }

    override suspend fun verify(passwordHash: String, passwordToVerify: String): Boolean =
        withContext(Dispatchers.Default) {
            Argon2PasswordEncoder().matches(passwordToVerify, passwordHash)
        }
}