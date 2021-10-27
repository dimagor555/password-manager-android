package ru.dimagor555.encryption.android

import android.content.Context

object AndroidEncryptionModuleHolder {
    private var moduleApi: AndroidEncryptionModuleApi? = null

    fun get() = requireNotNull(moduleApi) { "Module is not initialized" }

    fun init(applicationContext: Context) {
        moduleApi = AndroidEncryptionModuleApi(applicationContext)
    }
}