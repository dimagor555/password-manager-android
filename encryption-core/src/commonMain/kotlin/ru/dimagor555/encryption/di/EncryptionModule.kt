package ru.dimagor555.encryption.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.dimagor555.encryption.core.*
import ru.dimagor555.encryption.core.CommonCryptoKey
import ru.dimagor555.encryption.domain.CryptoKey

val encryptionModule = module {
    singleOf(::CommonCryptoKey) bind CryptoKey::class
    single {
        EncryptionModuleHolder
            .apply { init(get<CryptoKey>()) }
            .get()
    }
    factory { get<EncryptionModuleApi>().encryptor }
    factory { get<EncryptionModuleApi>().decryptor }
}