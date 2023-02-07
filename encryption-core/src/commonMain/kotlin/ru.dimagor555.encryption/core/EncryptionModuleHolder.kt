package ru.dimagor555.encryption.core

import ru.dimagor555.encryption.domain.CryptoKey

object EncryptionModuleHolder {
    private var moduleApi: EncryptionModuleApi? = null

    fun get(): EncryptionModuleApi = requireNotNull(moduleApi) { "Module is not initialized" }

    fun init(cryptoKey: CryptoKey) {
        moduleApi = EncryptionModuleApi(cryptoKey)
    }
}