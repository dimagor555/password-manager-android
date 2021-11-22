package ru.dimagor555.encryption.android.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.dimagor555.encryption.android.AndroidBase64
import ru.dimagor555.encryption.android.AndroidCryptoKey
import ru.dimagor555.encryption.android.EncryptedPrefsSecretKeyProvider
import ru.dimagor555.encryption.domain.Base64
import ru.dimagor555.encryption.domain.CryptoKey
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AndroidEncryptionModule {
    @Provides
    fun provideBase64(): Base64 = AndroidBase64()

    @Singleton
    @Provides
    fun provideCryptoKey(@ApplicationContext context: Context): CryptoKey {
        val secretKeyProvider = EncryptedPrefsSecretKeyProvider(context)
        return AndroidCryptoKey(secretKeyProvider)
    }
}