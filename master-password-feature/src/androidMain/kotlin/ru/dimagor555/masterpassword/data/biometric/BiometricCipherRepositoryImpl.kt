package ru.dimagor555.masterpassword.data.biometric

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dimagor555.encryption.symmetric.data.aes.AesGcmCipher
import ru.dimagor555.masterpassword.usecase.repository.BiometricCipherRepository
import javax.crypto.Cipher

internal class BiometricCipherRepositoryImpl(
    private val biometricSecretKeyProvider: BiometricSecretKeyProvider,
) : BiometricCipherRepository {

    override fun getCipherForEncryption(): Cipher =
        createAesCipher()
            .apply { initForEncryption() }
            .cipher

    private fun createAesCipher(): AesGcmCipher {
        val secretKey = biometricSecretKeyProvider.getOrCreate()
        return AesGcmCipher(getKey = { secretKey }, isGenerateIV = false)
    }

    override suspend fun encrypt(cipher: Cipher, plaintext: String): String =
        withContext(Dispatchers.Default) {
            val aesCipher = createAesCipher(cipher)
            aesCipher.encrypt(plaintext)
        }

    private fun createAesCipher(cipher: Cipher) = AesGcmCipher(
        getCipher = { cipher },
        getKey = { error("cipher already has authorized key") },
        isGenerateIV = false,
    )

    override fun getCipherForDecryption(ciphertext: String): Cipher =
        createAesCipher()
            .apply { initForDecryption(ciphertext) }
            .cipher

    override suspend fun decrypt(cipher: Cipher, ciphertext: String): String =
        withContext(Dispatchers.Default) {
            val aesCipher = createAesCipher(cipher)
            aesCipher.decrypt(ciphertext)
        }
}