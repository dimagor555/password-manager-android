package ru.dimagor555.encryption.core

import ru.dimagor555.encryption.domain.Base64
import ru.dimagor555.encryption.domain.CryptoKeyRepository
import ru.dimagor555.encryption.domain.Decryptor
import ru.dimagor555.encryption.domain.Encryptor

class EncryptionModuleApi internal constructor(base64: Base64) {
    val cryptoKeyRepository: CryptoKeyRepository = InMemoryCryptoKeyRepository()

    val encryptor: Encryptor = AesCryptor(cryptoKeyRepository, base64)
    val decryptor: Decryptor = AesCryptor(cryptoKeyRepository, base64)
}