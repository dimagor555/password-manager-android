package ru.dimagor555.encryption.core

import ru.dimagor555.encryption.domain.CryptoKey
import ru.dimagor555.encryption.domain.Decryptor
import ru.dimagor555.encryption.domain.Encryptor

class EncryptionModuleApi internal constructor(cryptoKey: CryptoKey) {
    val encryptor: Encryptor = AesCryptor(cryptoKey)
    val decryptor: Decryptor = AesCryptor(cryptoKey)
}