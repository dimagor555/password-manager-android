package ru.dimagor555.masterpassword.data.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.dimagor555.masterpassword.domain.*
import ru.dimagor555.masterpassword.domain.SettingsRepositoryImpl

val masterPasswordDataModule = module {
    singleOf(::PasswordHashDaoImpl) bind PasswordHashDao::class
    includes(hashingModule)
    singleOf(::MasterPasswordRepositoryImpl) bind MasterPasswordRepository::class
    singleOf(::SettingsRepositoryImpl) bind SettingsRepository::class
}