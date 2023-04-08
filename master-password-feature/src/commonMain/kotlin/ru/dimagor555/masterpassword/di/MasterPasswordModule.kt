package ru.dimagor555.masterpassword.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.dimagor555.masterpassword.domain.*
import ru.dimagor555.masterpassword.ui.editscreen.model.EditMasterPasswordUseCases
import ru.dimagor555.masterpassword.ui.loginscreen.model.LoginUseCases

val masterPasswordModule = module {
    includes(masterPasswordPlatformModule)

    singleOf(::PasswordHashDaoImpl) bind PasswordHashDao::class
    singleOf(::MasterPasswordRepositoryImpl) bind MasterPasswordRepository::class
    singleOf(::SettingsRepositoryImpl) bind SettingsRepository::class

    factoryOf(::LoginUseCases)
    factoryOf(::EditMasterPasswordUseCases)
}