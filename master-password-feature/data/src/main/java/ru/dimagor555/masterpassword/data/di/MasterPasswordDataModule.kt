package ru.dimagor555.masterpassword.data.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.dimagor555.masterpassword.data.PrefsPasswordHashDao
import ru.dimagor555.masterpassword.domain.MasterPasswordRepository
import ru.dimagor555.masterpassword.domain.MasterPasswordRepositoryImpl
import ru.dimagor555.masterpassword.domain.PasswordHashDao

val masterPasswordDataModule = module {
    singleOf(::PrefsPasswordHashDao) bind PasswordHashDao::class

    singleOf(::MasterPasswordRepositoryImpl) bind MasterPasswordRepository::class
}