package ru.dimagor555.password.ui.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.dimagor555.password.ui.commoneditscreen.model.CommonEditPasswordUseCases
import ru.dimagor555.password.ui.createscreen.model.CreatePasswordUseCases
import ru.dimagor555.password.ui.detailsscreen.model.PasswordDetailsUseCases
import ru.dimagor555.password.ui.editscreen.model.EditPasswordUseCases
import ru.dimagor555.password.ui.listscreen.model.PasswordListUseCases
import ru.dimagor555.password.usecase.field.CopyPasswordUseCase
import ru.dimagor555.password.usecase.password.CreatePasswordUseCase
import ru.dimagor555.password.usecase.field.DecryptPasswordUseCase
import ru.dimagor555.password.usecase.password.UpdatePasswordUseCase

internal val passwordUiModule = module {
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