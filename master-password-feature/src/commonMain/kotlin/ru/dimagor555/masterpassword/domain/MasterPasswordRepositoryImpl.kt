package ru.dimagor555.masterpassword.domain

import ru.dimagor555.encryption.symmetric.SymmetricEncryptionApi

class MasterPasswordRepositoryImpl(
    private val passwordHashDao: PasswordHashDao,
    private val hasher: Hasher,
    private val symmetricEncryptionApi: SymmetricEncryptionApi,
) : MasterPasswordRepository {

    override suspend fun hasPassword(): Boolean =
        passwordHashDao.hasPasswordHash()

    override suspend fun setPassword(password: String) {
        val newPasswordHash = hasher.hash(password)
        passwordHashDao.setPasswordHash(newPasswordHash)
        symmetricEncryptionApi.setKeyFromPassword(password)
    }

    override suspend fun verifyPassword(password: String): Boolean {
        val passwordHash = passwordHashDao.getPasswordHash()
        val isValid = hasher.verify(passwordHash = passwordHash, passwordToVerify = password)
        if (!isValid) {
            return false
        }
        symmetricEncryptionApi.setKeyFromPassword(password)
        return true
    }
}