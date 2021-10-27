package ru.dimagor555.encryption.android

import android.content.Context
import ru.dimagor555.encryption.domain.Base64
import ru.dimagor555.encryption.domain.CryptoKey

class AndroidEncryptionModuleApi internal constructor(applicationContext: Context) {
    private val secretKeyProvider = EncryptedPrefsSecretKeyProvider(applicationContext)

    val cryptoKey: CryptoKey = AndroidCryptoKey(secretKeyProvider)

    val base64: Base64 = AndroidBase64()
}