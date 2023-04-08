package ru.dimagor555.encryption.symmetric.data.aes

import ru.dimagor555.encryption.symmetric.data.repository.SymmetricKeyRepository
import ru.dimagor555.encryption.symmetric.domain.SymmetricDecryptor
import ru.dimagor555.encryption.symmetric.domain.SymmetricEncryptor

internal class AesCryptor(
    private val symmetricKeyRepository: SymmetricKeyRepository,
) : SymmetricEncryptor, SymmetricDecryptor {

    override fun encrypt(input: String) = createCipher().encrypt(input)

    override fun decrypt(input: String) = createCipher().decrypt(input)

    private fun createCipher() = SimpleAesGcmCipher(
        getKey = { symmetricKeyRepository.get() },
    )
}