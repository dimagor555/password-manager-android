package ru.dimagor555.encryption.symmetric.data.aes

import ru.dimagor555.encryption.symmetric.data.key.secretKey
import ru.dimagor555.encryption.symmetric.data.repository.SymmetricKeyRepository
import ru.dimagor555.encryption.symmetric.domain.SymmetricDecryptor
import ru.dimagor555.encryption.symmetric.domain.SymmetricEncryptor
import ru.dimagor555.encryption.symmetric.domain.SymmetricKey

internal class AesCryptor(
    private val customSymmetricKey: SymmetricKey?,
    private val symmetricKeyRepository: SymmetricKeyRepository,
) : SymmetricEncryptor, SymmetricDecryptor {

    override fun encrypt(plaintext: String) = createCipher().encrypt(plaintext)

    override fun decrypt(ciphertext: String) = createCipher().decrypt(ciphertext)

    private fun createCipher() = SimpleAesGcmCipher(
        getKey = getSymmetricKey()::secretKey,
    )

    private fun getSymmetricKey(): SymmetricKey =
        customSymmetricKey ?: symmetricKeyRepository.get()
}