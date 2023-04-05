package ru.dimagor555.masterpassword.domain

import ru.dimagor555.encryption.domain.AlgorithmProperties
import ru.dimagor555.encryption.data.SHA256Hasher
import ru.dimagor555.encryption.domain.CryptoKey
import javax.crypto.spec.SecretKeySpec

class MasterPasswordRepositoryImpl(
    private val passwordHashDao: PasswordHashDao,
    private val hasher: Hasher,
    private val cryptoKey: CryptoKey,
) : MasterPasswordRepository {

    override suspend fun hasPassword(): Boolean =
        passwordHashDao.hasPasswordHash()

    override suspend fun setPassword(password: String) {
        val newPasswordHash = hasher.hash(password)
        passwordHashDao.setPasswordHash(newPasswordHash)
        cryptoKey.secretKey = SecretKeySpec(
            SHA256Hasher().hash(password),
            AlgorithmProperties.AES_ALGORITHM,
        )
    }

    override suspend fun verifyPassword(password: String): Boolean {
        val passwordHash = passwordHashDao.getPasswordHash()
        val result = hasher.verify(passwordHash = passwordHash, passwordToVerify = password)
        if (result) {
            cryptoKey.secretKey = SecretKeySpec(
                SHA256Hasher().hash(password),
                AlgorithmProperties.AES_ALGORITHM,
            )
        }
        return result
    }
}