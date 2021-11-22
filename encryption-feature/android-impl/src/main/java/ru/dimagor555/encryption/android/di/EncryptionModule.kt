package ru.dimagor555.encryption.android.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.dimagor555.encryption.core.EncryptionModuleApi
import ru.dimagor555.encryption.core.EncryptionModuleHolder
import ru.dimagor555.encryption.domain.Base64
import ru.dimagor555.encryption.domain.CryptoKey
import ru.dimagor555.encryption.domain.Decryptor
import ru.dimagor555.encryption.domain.Encryptor
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object EncryptionModule {
    @Singleton
    @Provides
    internal fun provideEncryptionModuleApi(
        base64: Base64,
        cryptoKey: CryptoKey
    ): EncryptionModuleApi {
        EncryptionModuleHolder.init(base64)
        val encryptionModule = EncryptionModuleHolder.get()
        encryptionModule.cryptoKeyRepository.setKey(cryptoKey)
        return encryptionModule
    }

    @Provides
    fun provideEncryptor(encryptionModule: EncryptionModuleApi): Encryptor =
        encryptionModule.encryptor

    @Provides
    fun provideDecryptor(encryptionModule: EncryptionModuleApi): Decryptor =
        encryptionModule.decryptor
}