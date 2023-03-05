package ru.dimagor555.syncpassintegration.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.dimagor555.synchronization.repository.SyncPasswordRepository
import ru.dimagor555.syncpassintegration.data.SyncPasswordRepositoryImpl

val syncPasswordIntegrationModule = module {
    singleOf(::SyncPasswordRepositoryImpl) bind SyncPasswordRepository::class
}
