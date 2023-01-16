package ru.dimagor555.masterpassword.data.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.dimagor555.masterpassword.domain.MasterPasswordRepository
import ru.dimagor555.masterpassword.domain.MasterPasswordRepositoryImpl

val masterPasswordDataModule = module {
    includes(passwordHashDaoModule)
    includes(hashingModule)
    singleOf(::MasterPasswordRepositoryImpl) bind MasterPasswordRepository::class
}