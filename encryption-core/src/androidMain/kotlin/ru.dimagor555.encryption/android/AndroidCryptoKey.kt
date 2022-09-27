package ru.dimagor555.encryption.android

import ru.dimagor555.encryption.domain.CryptoKey
import javax.crypto.SecretKey

internal data class AndroidCryptoKey(
    private val secretKeyProvider: EncryptedPrefsSecretKeyProvider
) : CryptoKey {
    override val secretKey: SecretKey
        get() = secretKeyProvider.provideKey()
}