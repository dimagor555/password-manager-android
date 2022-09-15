package ru.dimagor555.encryption.android.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.dimagor555.encryption.android.AesSecretKeyGenerator
import ru.dimagor555.encryption.android.AndroidBase64
import ru.dimagor555.encryption.android.AndroidCryptoKey
import ru.dimagor555.encryption.android.EncryptedPrefsSecretKeyProvider
import ru.dimagor555.encryption.domain.Base64
import ru.dimagor555.encryption.domain.CryptoKey

val androidEncryptionModule = module {
    factoryOf(::AndroidBase64) bind Base64::class
    factory { AesSecretKeyGenerator() }
    singleOf(::EncryptedPrefsSecretKeyProvider)
    singleOf(::AndroidCryptoKey) bind CryptoKey::class
}