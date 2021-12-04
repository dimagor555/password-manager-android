package ru.dimagor555.masterpassword.hashing.di

import com.lambdapioneer.argon2kt.Argon2Kt
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.dimagor555.masterpassword.domain.Hasher
import ru.dimagor555.masterpassword.hashing.Argon2Hasher
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object HashingModule {
    @Singleton
    @Provides
    internal fun provideArgon2Kt() = Argon2Kt()

    @Provides
    internal fun provideHasher(hasher: Argon2Hasher): Hasher = hasher
}