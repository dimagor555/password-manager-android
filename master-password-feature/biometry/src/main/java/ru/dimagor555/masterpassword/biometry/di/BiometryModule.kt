package ru.dimagor555.masterpassword.biometry.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.dimagor555.masterpassword.biometry.BiometryRepositoryImpl
import ru.dimagor555.masterpassword.repository.BiometryRepository

@InstallIn(SingletonComponent::class)
@Module
object BiometryModule {
    @Provides
    internal fun provideBiometryRepository(repo: BiometryRepositoryImpl): BiometryRepository = repo
}