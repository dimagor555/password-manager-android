package ru.dimagor555.encryption.asymmetric.domain

import ru.dimagor555.encryption.asymmetric.domain.key.AsymmetricPublicKey

interface AsymmetricEncryptor {

    fun encrypt(plaintext: String, asymmetricPublicKey: AsymmetricPublicKey): String
}