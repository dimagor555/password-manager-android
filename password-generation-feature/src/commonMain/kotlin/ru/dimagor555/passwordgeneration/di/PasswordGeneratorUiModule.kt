package ru.dimagor555.passwordgeneration.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.dimagor555.passwordgeneration.ui.screen.model.PasswordGenerationUseCases

val passwordGenerationModule = module {
    factoryOf(::PasswordGenerationUseCases)
}