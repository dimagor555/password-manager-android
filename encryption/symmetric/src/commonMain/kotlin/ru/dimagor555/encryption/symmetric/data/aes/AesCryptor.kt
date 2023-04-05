 package ru.dimagor555.encryption.symmetric.data.aes

import ru.dimagor555.encryption.symmetric.domain.SymmetricDecryptor
import ru.dimagor555.encryption.symmetric.domain.SymmetricEncryptor
import javax.crypto.SecretKey

internal class AesCryptor(
    private val getKey: () -> SecretKey,
) : SymmetricEncryptor, SymmetricDecryptor {

    override fun encrypt(input: String) = createCipher().encrypt(input)

    override fun decrypt(input: String) = createCipher().decrypt(input)

    private fun createCipher() = AesGcmCipher(getKey = getKey)
}