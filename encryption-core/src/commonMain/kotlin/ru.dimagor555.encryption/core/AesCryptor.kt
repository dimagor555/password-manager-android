package ru.dimagor555.encryption.core

import ru.dimagor555.encryption.domain.*

internal class AesCryptor(private val cryptoKey: CryptoKey) : Encryptor, Decryptor {
    override fun encrypt(input: String) = createCipher().encrypt(input)

    override fun decrypt(input: String) = createCipher().decrypt(input)

    private fun createCipher() = AesGcmCipher(
        key = cryptoKey.secretKey ?: throw NoCryptoKeyException(),
    )
}