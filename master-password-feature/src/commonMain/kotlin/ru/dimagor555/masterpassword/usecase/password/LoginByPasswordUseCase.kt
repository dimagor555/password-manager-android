package ru.dimagor555.masterpassword.usecase.password

import ru.dimagor555.encryption.symmetric.SymmetricEncryptionApi
import ru.dimagor555.masterpassword.usecase.password.repository.MasterPasswordHashRepository
import ru.dimagor555.masterpassword.usecase.password.repository.PasswordHasher

internal class LoginByPasswordUseCase(
    private val hasher: PasswordHasher,
    private val masterPasswordHashRepository: MasterPasswordHashRepository,
    private val symmetricEncryptionApi: SymmetricEncryptionApi,
) {

    suspend operator fun invoke(password: String): Result {
        verifyPasswordOrElse(password) { return Result.InvalidPassword }
        symmetricEncryptionApi.setKeyFromPassword(password)
        return Result.Success
    }

    private suspend inline fun verifyPasswordOrElse(
        password: String,
        onError: () -> Nothing,
    ) {
        val passwordHash = masterPasswordHashRepository.getPasswordHash()
        val isValid = hasher.verify(passwordHash = passwordHash, passwordToVerify = password)
        if (!isValid) {
            onError()
        }
    }

    sealed interface Result {
        object Success : Result
        object InvalidPassword : Result
    }
}