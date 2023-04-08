package ru.dimagor555.masterpassword.usecase

import ru.dimagor555.encryption.symmetric.SymmetricEncryptionApi
import ru.dimagor555.masterpassword.usecase.biometric.repository.BiometricSymmetricKeyRepository
import ru.dimagor555.masterpassword.usecase.repository.BiometricCipherRepository
import javax.crypto.Cipher

internal class SetUpBiometricLoginUsecase(
    private val biometricSymmetricKeyRepository: BiometricSymmetricKeyRepository,
    private val biometricCipherRepository: BiometricCipherRepository,
    private val symmetricEncryptionApi: SymmetricEncryptionApi,
) {

    data class Params(
        val authorizeCipher: suspend (Cipher) -> Cipher?,
    )

    suspend operator fun invoke(params: Params): Result =
        runCatching { setUpBiometricLogin(params) }
            .getOrElse { Result.Error }

    private suspend fun setUpBiometricLogin(params: Params): Result {
        val decryptedKey = symmetricEncryptionApi.getKeyBase64()
        val cipher = biometricCipherRepository.getCipherForEncryption()
        val authorizedCipher = params.authorizeCipher(cipher) ?: return Result.Error
        val encryptedKey = biometricCipherRepository.encrypt(authorizedCipher, decryptedKey)
        biometricSymmetricKeyRepository.set(encryptedKey)
        return Result.Success
    }

    sealed interface Result {
        object Success : Result
        object Error : Result
    }
}