package ru.dimagor555.masterpassword.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.dimagor555.masterpassword.data.PrefsPasswordHashDao
import ru.dimagor555.masterpassword.domain.Hasher
import ru.dimagor555.masterpassword.domain.MasterPasswordRepository
import ru.dimagor555.masterpassword.domain.MasterPasswordRepositoryImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MasterPasswordDataModule {
    @Singleton
    @Provides
    internal fun provideMasterPasswordRepository(
        dao: PrefsPasswordHashDao,
        hasher: Hasher
    ): MasterPasswordRepository = MasterPasswordRepositoryImpl(dao, hasher)
}