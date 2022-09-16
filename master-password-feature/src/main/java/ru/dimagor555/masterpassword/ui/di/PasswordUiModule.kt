package ru.dimagor555.masterpassword.ui.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.dimagor555.masterpassword.data.di.masterPasswordDataModule
import ru.dimagor555.masterpassword.hashing.di.hashingModule
import ru.dimagor555.masterpassword.ui.editscreen.EditMasterPasswordViewModel
import ru.dimagor555.masterpassword.ui.editscreen.model.EditMasterPasswordUseCases
import ru.dimagor555.masterpassword.ui.loginscreen.LoginViewModel
import ru.dimagor555.masterpassword.ui.loginscreen.model.LoginUseCases
import ru.dimagor555.masterpassword.ui.startscreen.StartViewModel

val masterPasswordUiModule = module {
    includes(masterPasswordDataModule, hashingModule)

    factoryOf(::LoginUseCases)
    factoryOf(::EditMasterPasswordUseCases)

    viewModelOf(::LoginViewModel)
    viewModelOf(::EditMasterPasswordViewModel)
    viewModelOf(::StartViewModel)
}