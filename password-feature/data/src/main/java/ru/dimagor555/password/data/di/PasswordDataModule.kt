package ru.dimagor555.password.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.dimagor555.password.data.ClipboardRepositoryImpl
import ru.dimagor555.password.data.InMemoryPasswordFilterRepository
import ru.dimagor555.password.data.RoomPasswordRepository
import ru.dimagor555.password.data.db.PasswordDatabase
import ru.dimagor555.password.repository.ClipboardRepository
import ru.dimagor555.password.repository.PasswordFilterRepository
import ru.dimagor555.password.repository.PasswordRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object PasswordDataModule {
    @Singleton
    @Provides
    internal fun providePasswordDatabase(@ApplicationContext applicationContext: Context) =
        Room.databaseBuilder(
            applicationContext,
            PasswordDatabase::class.java,
            PasswordDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    internal fun provideClipboardRepository(repository: ClipboardRepositoryImpl): ClipboardRepository {
        return repository
    }

    @Singleton
    @Provides
    internal fun providePasswordRepository(db: PasswordDatabase): PasswordRepository {
        return RoomPasswordRepository(db.passwordModelDao())
    }

    @Singleton
    @Provides
    internal fun providePasswordFilterRepository(repository: InMemoryPasswordFilterRepository): PasswordFilterRepository {
        return repository
    }
}