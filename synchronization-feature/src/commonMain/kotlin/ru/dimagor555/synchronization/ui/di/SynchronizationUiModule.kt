package ru.dimagor555.synchronization.ui.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.dimagor555.synchronization.ui.deviceslistscreen.store.DevicesListUseCases
import ru.dimagor555.synchronization.ui.resultsyncscreen.store.ResultSyncUseCases
import ru.dimagor555.synchronization.ui.syncscreen.store.SyncUseCases

val synchronizationUiModule = module {
    factoryOf(::DevicesListUseCases)
    factoryOf(::SyncUseCases)
    factoryOf(::ResultSyncUseCases)
}
