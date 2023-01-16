package ru.dimagor555.masterpassword.ui.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.dimagor555.masterpassword.data.di.masterPasswordDataModule
import ru.dimagor555.masterpassword.ui.editscreen.model.EditMasterPasswordUseCases
import ru.dimagor555.masterpassword.ui.loginscreen.model.LoginUseCases

val masterPasswordModule = module {
    factoryOf(::LoginUseCases)
    factoryOf(::EditMasterPasswordUseCases)
    includes(masterPasswordDataModule)
}