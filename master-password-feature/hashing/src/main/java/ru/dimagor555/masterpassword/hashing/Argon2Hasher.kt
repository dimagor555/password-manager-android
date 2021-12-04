package ru.dimagor555.masterpassword.hashing

import com.lambdapioneer.argon2kt.Argon2Kt
import com.lambdapioneer.argon2kt.Argon2Mode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dimagor555.masterpassword.domain.Hasher
import java.security.SecureRandom
import javax.inject.Inject

internal class Argon2Hasher @Inject constructor(
    private val argon2: Argon2Kt
) : Hasher {
    override suspend fun hash(password: String) = withContext(Dispatchers.Default) {
        argon2.hash(
            mode = DEFAULT_MODE,
            password = password.toByteArray(),
            salt = generateSalt()
        ).encodedOutputAsString()
    }

    private fun generateSalt(): ByteArray {
        val salt = ByteArray(DEFAULT_SALT_SIZE_IN_BYTES)
        val random = SecureRandom()
        random.nextBytes(salt)
        return salt
    }

    override suspend fun verify(passwordHash: String, passwordToVerify: String) =
        withContext(Dispatchers.Default) {
            argon2.verify(
                mode = DEFAULT_MODE,
                encoded = passwordHash,
                password = passwordToVerify.toByteArray()
            )
        }

    companion object {
        private val DEFAULT_MODE = Argon2Mode.ARGON2_ID
        private const val DEFAULT_SALT_SIZE_IN_BYTES = 16
    }
}