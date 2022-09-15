package ru.dimagor555.password.ui.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.dimagor555.password.data.di.passwordDataModule
import ru.dimagor555.password.ui.commoneditscreen.model.CommonEditPasswordUseCases
import ru.dimagor555.password.ui.createscreen.CreatePasswordViewModel
import ru.dimagor555.password.ui.createscreen.model.CreatePasswordUseCases
import ru.dimagor555.password.ui.detailsscreen.PasswordDetailsViewModel
import ru.dimagor555.password.ui.detailsscreen.model.PasswordDetailsUseCases
import ru.dimagor555.password.ui.editscreen.EditPasswordViewModel
import ru.dimagor555.password.ui.editscreen.model.EditPasswordUseCases
import ru.dimagor555.password.ui.listscreen.PasswordListViewModel
import ru.dimagor555.password.ui.listscreen.model.PasswordListUseCases
import ru.dimagor555.password.usecase.*

val passwordUiModule = module {
    includes(passwordDataModule)

    viewModelOf(::CreatePasswordViewModel)
    viewModelOf(::PasswordDetailsViewModel)
    viewModelOf(::EditPasswordViewModel)
    viewModelOf(::PasswordListViewModel)

    factoryOf(::CreatePasswordUseCases)
    factoryOf(::CommonEditPasswordUseCases)
    factoryOf(::PasswordDetailsUseCases)
    factoryOf(::EditPasswordUseCases)
    factoryOf(::PasswordListUseCases)

    factoryOf(::UpdatePasswordUseCase)
    factoryOf(::CreatePasswordUseCase)
    factoryOf(::CopyPasswordUseCase)
    factoryOf(::DecryptPasswordUseCase)
}