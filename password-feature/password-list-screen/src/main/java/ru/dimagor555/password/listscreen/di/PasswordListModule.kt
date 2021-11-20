package ru.dimagor555.password.listscreen.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import ru.dimagor555.encryption.android.AndroidEncryptionModuleHolder
import ru.dimagor555.encryption.core.EncryptionModuleHolder
import ru.dimagor555.encryption.domain.Decryptor
import ru.dimagor555.password.data.PasswordModuleHolder
import ru.dimagor555.password.listscreen.PasswordListUseCases
import ru.dimagor555.password.repository.ClipboardRepository
import ru.dimagor555.password.repository.PasswordRepository

@Module
@InstallIn(ViewModelComponent::class)
internal object PasswordListModule {
    @Provides
    @ViewModelScoped
    fun provideClipboardRepository(@ApplicationContext context: Context): ClipboardRepository {
        PasswordModuleHolder.init(context)
        return PasswordModuleHolder.get().clipboardRepository
    }

    @Provides
    @ViewModelScoped
    fun providePasswordRepository() = PasswordModuleHolder.get().passwordRepository

    @Provides
    @ViewModelScoped
    fun provideDecryptor(@ApplicationContext context: Context): Decryptor {
        AndroidEncryptionModuleHolder.init(context)
        val androidEncryption = AndroidEncryptionModuleHolder.get()
        EncryptionModuleHolder.init(androidEncryption.base64)
        val encryption = EncryptionModuleHolder.get()
        encryption.cryptoKeyRepository.setKey(androidEncryption.cryptoKey)
        return encryption.decryptor
    }

    @Provides
    @ViewModelScoped
    fun providePasswordListUseCases(
        clipboardRepository: ClipboardRepository,
        passwordRepository: PasswordRepository,
        decryptor: Decryptor
    ) = PasswordListUseCases(passwordRepository, clipboardRepository, decryptor)
}