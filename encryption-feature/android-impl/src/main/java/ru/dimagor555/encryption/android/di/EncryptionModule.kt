package ru.dimagor555.encryption.android.di

import org.koin.dsl.module
import ru.dimagor555.encryption.core.EncryptionModuleApi
import ru.dimagor555.encryption.core.EncryptionModuleHolder
import ru.dimagor555.encryption.domain.Base64

val encryptionModule = module {
    single {
        EncryptionModuleHolder
            .apply { init(get<Base64>()) }
            .get()
            .apply { cryptoKeyRepository.setKey(get()) }
    }
    factory { get<EncryptionModuleApi>().encryptor }
    factory { get<EncryptionModuleApi>().decryptor }
}