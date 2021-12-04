package ru.dimagor555.masterpassword.domain

class MasterPasswordRepositoryImpl(
    private val passwordHashDao: PasswordHashDao,
    private val hasher: Hasher
) : MasterPasswordRepository {
    override suspend fun hasPassword() = passwordHashDao.hasPasswordHash()

    override suspend fun setPassword(password: String) {
        val newPasswordHash = hasher.hash(password)
        passwordHashDao.setPasswordHash(newPasswordHash)
    }

    override suspend fun verifyPassword(password: String): Boolean {
        val passwordHash = passwordHashDao.getPasswordHash()
        return hasher.verify(passwordHash = passwordHash, passwordToVerify = password)
    }
}