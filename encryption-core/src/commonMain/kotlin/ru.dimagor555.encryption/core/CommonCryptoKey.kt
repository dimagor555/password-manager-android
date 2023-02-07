package ru.dimagor555.encryption.core

import ru.dimagor555.encryption.domain.CryptoKey
import javax.crypto.SecretKey

class CommonCryptoKey : CryptoKey {
    override var secretKey: SecretKey? = null
}
