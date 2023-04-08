package ru.dimagor555.encryption.asymmetric.domain

import ru.dimagor555.encryption.asymmetric.domain.key.AsymmetricPrivateKey

interface AsymmetricDecryptor {

    fun decrypt(ciphertext: String, asymmetricPrivateKey: AsymmetricPrivateKey): String
}