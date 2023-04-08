package ru.dimagor555.encryption.symmetric.data.aes

import javax.crypto.SecretKey

internal class SimpleAesGcmCipher(
    private val getKey: () -> SecretKey,
) {

    private val cipher by lazy { AesGcmCipher(getKey = getKey) }

    fun encrypt(plaintext: String): String =
        cipher.run {
            initForEncryption()
            encrypt(plaintext)
        }

    fun decrypt(ciphertext: String): String =
        cipher.run {
            initForDecryption(ciphertext)
            decrypt(ciphertext)
        }
}