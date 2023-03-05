package ru.dimagor555.synchronization.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.dimagor555.synchronization.data.*
import ru.dimagor555.synchronization.repository.ClientRepository
import ru.dimagor555.synchronization.repository.ReceivePasswordApi
import ru.dimagor555.synchronization.repository.ServerRepository
import ru.dimagor555.synchronization.repository.SendPasswordApi

val synchronizationModule = module {
    single { ConnectionAddress() }
    singleOf(::ServerRepositoryImpl) bind ServerRepository::class
    singleOf(::ClientRepositoryImpl) bind ClientRepository::class

    singleOf(::SendPasswordApiImpl) bind SendPasswordApi::class
    singleOf(::ReceivePasswordApiImpl) bind ReceivePasswordApi::class
}
