package ru.dimagor555.encryption.core

import ru.dimagor555.encryption.domain.Base64

object EncryptionModuleHolder {
    /*
    TODO: 04.12.2021 it's good idea with *requireNotNull*, especially with custom error message.
          But pay attention that *Delegates.notNull* is also suitable.
     */
    private var moduleApi: EncryptionModuleApi? = null

    fun get(): EncryptionModuleApi = requireNotNull(moduleApi) { "Module is not initialized" }

    fun init(base64: Base64) {
        moduleApi = EncryptionModuleApi(base64)
    }
}