package ru.dimagor555.encryption.core

import ru.dimagor555.encryption.domain.Base64
import ru.dimagor555.encryption.domain.CryptoKeyRepository
import ru.dimagor555.encryption.domain.Decryptor
import ru.dimagor555.encryption.domain.Encryptor

internal class AesCryptor(
    private val cryptoKeyRepository: CryptoKeyRepository,
    private val base64: Base64
) : Encryptor, Decryptor {

    override fun encrypt(input: String) = createCipher().encrypt(input)

    override fun decrypt(input: String) = createCipher().decrypt(input)

    private fun createCipher() = AesGcmCipher(
        key = cryptoKeyRepository.getKey().secretKey,
        base64 = base64
    )
}