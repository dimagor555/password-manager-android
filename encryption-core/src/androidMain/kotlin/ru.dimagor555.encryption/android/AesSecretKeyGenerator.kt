package ru.dimagor555.encryption.android

import ru.dimagor555.encryption.core.AlgorithmProperties
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

internal class AesSecretKeyGenerator {
    fun generateKey(): SecretKey =
        KeyGenerator.getInstance(AlgorithmProperties.AES_ALGORITHM)
            .apply { init(AlgorithmProperties.AES_KEY_SIZE_BITS) }
            .generateKey()
}