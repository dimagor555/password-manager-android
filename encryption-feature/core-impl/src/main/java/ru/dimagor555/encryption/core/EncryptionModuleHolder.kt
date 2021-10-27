package ru.dimagor555.encryption.core

import ru.dimagor555.encryption.domain.Base64

object EncryptionModuleHolder {
    private var moduleApi: EncryptionModuleApi? = null

    fun get(): EncryptionModuleApi = requireNotNull(moduleApi) { "Module is not initialized" }

    fun init(base64: Base64) {
        moduleApi = EncryptionModuleApi(base64)
    }
}