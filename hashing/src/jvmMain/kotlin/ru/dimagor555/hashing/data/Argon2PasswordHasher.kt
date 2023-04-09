package ru.dimagor555.hashing.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.bouncycastle.crypto.generators.Argon2BytesGenerator
import org.bouncycastle.crypto.params.Argon2Parameters
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder
import ru.dimagor555.hashing.domain.Argon2Params
import ru.dimagor555.hashing.domain.Argon2SymmetricKeySaltGenerator
import ru.dimagor555.hashing.domain.PasswordHasher

internal class Argon2PasswordHasher(
    private val symmetricKeySaltGenerator: Argon2SymmetricKeySaltGenerator,
) : PasswordHasher {

    override suspend fun hash(password: String): String = withContext(Dispatchers.Default) {
        Argon2PasswordEncoder().encode(password)
    }

    override suspend fun hashForSymmetricKey(password: String): ByteArray =
        withContext(Dispatchers.Default) {
            val passwordBytes = password.toByteArray()
            val params = createArgon2Parameters(passwordBytes)
            generateArgon2Hash(params, passwordBytes)
        }

    private suspend fun createArgon2Parameters(passwordBytes: ByteArray): Argon2Parameters {
        val salt = symmetricKeySaltGenerator.generateFromPassword(passwordBytes)
        return Argon2Parameters
            .Builder(Argon2Parameters.ARGON2_id)
            .withSalt(salt)
            .withParallelism(Argon2Params.PARALLELISM)
            .withMemoryAsKB(Argon2Params.MEMORY_KIBIBYTES)
            .withIterations(Argon2Params.ITERATIONS)
            .build()
    }

    private fun generateArgon2Hash(params: Argon2Parameters, passwordBytes: ByteArray): ByteArray {
        val hash = ByteArray(Argon2Params.HASH_LENGTH_BYTES)
        Argon2BytesGenerator().apply {
            init(params)
            generateBytes(passwordBytes, hash)
        }
        return hash
    }

    override suspend fun verify(
        passwordHash: String,
        passwordToVerify: String
    ): Boolean = withContext(Dispatchers.Default) {
        Argon2PasswordEncoder().matches(passwordToVerify, passwordHash)
    }
}