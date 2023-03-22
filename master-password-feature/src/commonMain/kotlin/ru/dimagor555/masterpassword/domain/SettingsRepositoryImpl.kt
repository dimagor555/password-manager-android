package ru.dimagor555.masterpassword.domain

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set

internal class SettingsRepositoryImpl : SettingsRepository {
    private val settings: Settings = Settings()

    @Suppress("UNCHECKED_CAST")
    override suspend fun <T : Any> get(property: SettingsRepository.Property<T>): T? =
        property.run {
            when (kClass) {
                String::class -> settings.getStringOrNull(key) as T?
                Boolean::class -> settings.getBooleanOrNull(key) as T?
                else -> error("Invalid type")
            }
        }

    override suspend fun <T : Any> set(property: SettingsRepository.Property<T>, value: T?) =
        property.run {
            when (kClass) {
                String::class -> settings[key] = value as String?
                Boolean::class -> settings[key] = value as Boolean?
                else -> error("Invalid type")
            }
        }

    override suspend fun <T : Any> has(property: SettingsRepository.Property<T>): Boolean =
        property.run {
            when (kClass) {
                String::class -> settings.hasKey(key)
                Boolean::class -> settings.hasKey(key)
                else -> error("Invalid type")
            }
        }
}