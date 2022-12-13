package ru.dimagor555.password.di

import org.koin.dsl.module
import ru.dimagor555.password.data.di.passwordDataModule
import ru.dimagor555.password.ui.di.passwordUiModule

val passwordModule = module {
    includes(
        passwordDataModule,
        passwordUiModule,
    )
}
