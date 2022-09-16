package ru.dimagor555.passwordgeneration.ui.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.dimagor555.passwordgeneration.ui.screen.PasswordGenerationViewModel
import ru.dimagor555.passwordgeneration.ui.screen.model.PasswordGenerationUseCases

val passwordGenerationUiModule = module {
    viewModelOf(::PasswordGenerationViewModel)

    factoryOf(::PasswordGenerationUseCases)
}