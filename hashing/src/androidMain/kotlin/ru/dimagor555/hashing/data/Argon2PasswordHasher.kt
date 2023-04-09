package ru.dimagor555.hashing.data

import com.lambdapioneer.argon2kt.Argon2Kt
import com.lambdapioneer.argon2kt.Argon2Mode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dimagor555.hashing.domain.Argon2Params
import ru.dimagor555.hashing.domain.Argon2SymmetricKeySaltGenerator
import ru.dimagor555.hashing.domain.PasswordHasher

internal class Argon2PasswordHasher(
    private val randomSaltGenerator: Argon2SecureRandomSaltGenerator,
    private val symmetricKeySaltGenerator: Argon2SymmetricKeySaltGenerator,
) : PasswordHasher {

    private val argon2 by lazy { Argon2Kt() }

    override suspend fun hash(password: String): String =
        withContext(Dispatchers.Default) {
            argon2
                .hash(
                    mode = DEFAULT_MODE,
                    password = password.toByteArray(),
                    salt = randomSaltGenerator.generate(),
                    tCostInIterations = Argon2Params.ITERATIONS,
                    mCostInKibibyte = Argon2Params.MEMORY_KIBIBYTES,
                    parallelism = Argon2Params.PARALLELISM,
                    hashLengthInBytes = Argon2Params.HASH_LENGTH_BYTES,
                )
                .encodedOutputAsString()
        }

    override suspend fun hashForSymmetricKey(password: String): ByteArray =
        withContext(Dispatchers.Default) {
            val passwordBytes = password.toByteArray()
            val salt = symmetricKeySaltGenerator.generateFromPassword(passwordBytes)
            argon2
                .hash(
                    mode = DEFAULT_MODE,
                    password = passwordBytes,
                    salt = salt,
                    tCostInIterations = Argon2Params.ITERATIONS,
                    mCostInKibibyte = Argon2Params.MEMORY_KIBIBYTES,
                    parallelism = Argon2Params.PARALLELISM,
                    hashLengthInBytes = Argon2Params.HASH_LENGTH_BYTES,
                )
                .rawHashAsByteArray()
        }

    override suspend fun verify(passwordHash: String, passwordToVerify: String): Boolean =
        withContext(Dispatchers.Default) {
            argon2.verify(
                mode = DEFAULT_MODE,
                encoded = passwordHash,
                password = passwordToVerify.toByteArray(),
            )
        }

    companion object {
        private val DEFAULT_MODE = Argon2Mode.ARGON2_ID
    }
}