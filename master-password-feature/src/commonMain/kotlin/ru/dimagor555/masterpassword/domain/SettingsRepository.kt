package ru.dimagor555.masterpassword.domain

import kotlin.reflect.KClass

interface SettingsRepository {
    suspend fun <T : Any> get(property: Property<T>): T?

    suspend fun <T : Any> set(property: Property<T>, value: T?)

    suspend fun <T: Any> has(property: Property<T>): Boolean

    data class Property<T : Any>(
        val key: String,
        val kClass: KClass<T>
    )

    companion object Properties {
        val MASTER_PASSWORD_HASH = Property(key = "MasterPasswordHash", kClass = String::class)
        val APP_DARK_THEME = Property(key = "appDarkTheme", kClass = Boolean::class)
    }
}
