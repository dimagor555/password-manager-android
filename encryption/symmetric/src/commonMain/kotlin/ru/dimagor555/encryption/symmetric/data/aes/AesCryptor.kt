package ru.dimagor555.encryption.symmetric.data.aes

import ru.dimagor555.encryption.symmetric.data.key.secretKey
import ru.dimagor555.encryption.symmetric.data.repository.SymmetricKeyRepository
import ru.dimagor555.encryption.symmetric.domain.SymmetricDecryptor
import ru.dimagor555.encryption.symmetric.domain.SymmetricEncryptor
import ru.dimagor555.encryption.symmetric.domain.SymmetricKey

internal class AesCryptor(
    private val symmetricKeyRepository: SymmetricKeyRepository,
) : SymmetricEncryptor, SymmetricDecryptor {

    override fun encrypt(plaintext: String, customKey: SymmetricKey?): String =
        createCipher(customKey)
            .encrypt(plaintext)

    override fun decrypt(ciphertext: String, customKey: SymmetricKey?): String =
        createCipher(customKey)
            .decrypt(ciphertext)

    private fun createCipher(customKey: SymmetricKey?): SimpleAesGcmCipher {
        val symmetricKey = customKey ?: symmetricKeyRepository.get()
        return SimpleAesGcmCipher(getKey = symmetricKey::secretKey)
    }
}