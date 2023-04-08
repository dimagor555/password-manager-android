package ru.dimagor555.encryption.symmetric.data.aes

import javax.crypto.SecretKey

internal class SimpleAesGcmCipher(
    private val getKey: () -> SecretKey,
) {

    private val cipher by lazy { AesGcmCipher(getKey = getKey) }

    fun encrypt(input: String): String =
        cipher.run {
            initForEncryption()
            encrypt(input)
        }

    fun decrypt(input: String): String =
        cipher.run {
            initForDecryption(input)
            decrypt(input)
        }
}