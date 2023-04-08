package ru.dimagor555.masterpassword.usecase

import ru.dimagor555.encryption.symmetric.SymmetricEncryptionApi
import ru.dimagor555.masterpassword.usecase.biometric.repository.BiometricSymmetricKeyRepository
import ru.dimagor555.masterpassword.usecase.repository.BiometricCipherRepository
import javax.crypto.Cipher

internal class LoginByBiometricUsecase(
    private val biometricSymmetricKeyRepository: BiometricSymmetricKeyRepository,
    private val biometricCipherRepository: BiometricCipherRepository,
    private val symmetricEncryptionApi: SymmetricEncryptionApi,
) {

    data class Params(
        val authorizeCipher: suspend (Cipher) -> Cipher?,
    )

    suspend operator fun invoke(params: Params): Result =
        runCatching { loginByBiometric(params) }
            .getOrElse { Result.Error }

    private suspend fun loginByBiometric(params: Params): Result {
        val encryptedKey = biometricSymmetricKeyRepository.getOrNull() ?: return Result.Error
        val cipher = biometricCipherRepository.getCipherForDecryption(encryptedKey)
        val authorizedCipher = params.authorizeCipher(cipher) ?: return Result.Error
        val decryptedKey = biometricCipherRepository.decrypt(authorizedCipher, encryptedKey)
        symmetricEncryptionApi.setKeyFromBase64(decryptedKey)
        return Result.Success
    }

    sealed interface Result {
        object Success : Result
        object Error : Result
    }
}