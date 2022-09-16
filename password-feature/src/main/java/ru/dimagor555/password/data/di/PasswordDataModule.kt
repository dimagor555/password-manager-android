package ru.dimagor555.password.data.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.dimagor555.password.data.ClipboardRepositoryImpl
import ru.dimagor555.password.data.InMemoryPasswordFilterRepository
import ru.dimagor555.password.data.RoomPasswordRepository
import ru.dimagor555.password.data.db.PasswordDatabase
import ru.dimagor555.password.repository.ClipboardRepository
import ru.dimagor555.password.repository.PasswordFilterRepository
import ru.dimagor555.password.repository.PasswordRepository

val passwordDataModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            PasswordDatabase::class.java,
            PasswordDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }
    factory { get<PasswordDatabase>().passwordModelDao() }
    factoryOf(::ClipboardRepositoryImpl) bind ClipboardRepository::class
    factoryOf(::RoomPasswordRepository) bind PasswordRepository::class
    singleOf(::InMemoryPasswordFilterRepository) bind PasswordFilterRepository::class
}