package ru.dimagor555.encryption.android

import ru.dimagor555.encryption.core.AlgorithmProperties
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

internal class AesSecretKeyGenerator {
    // TODO: 04.12.2021 just to show that kotlin idioms is always better)
    fun generateKey(): SecretKey =
        KeyGenerator.getInstance(AlgorithmProperties.AES_ALGORITHM)
            .apply { init(AlgorithmProperties.AES_KEY_SIZE_BITS) }
            .generateKey()
}