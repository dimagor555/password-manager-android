package ru.dimagor555.synchronization.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.dimagor555.synchronization.data.connectionaddress.model.ConnectionAddress
import ru.dimagor555.synchronization.data.rest.api.SendPasswordApiImpl
import ru.dimagor555.synchronization.data.rest.repository.ClientRepositoryImpl
import ru.dimagor555.synchronization.data.rest.repository.ReceivePasswordRepositoryImpl
import ru.dimagor555.synchronization.data.rest.repository.SendPasswordRepositoryImpl
import ru.dimagor555.synchronization.data.rest.repository.ServerRepositoryImpl
import ru.dimagor555.synchronization.data.syncdevice.api.SyncDeviceApiImpl
import ru.dimagor555.synchronization.data.syncdevice.repository.SyncDeviceRepositoryImpl
import ru.dimagor555.synchronization.data.syncresult.SyncResultRepositoryImpl
import ru.dimagor555.synchronization.data.syncstatus.SyncStatusRepositoryImpl
import ru.dimagor555.synchronization.ui.di.synchronizationUiModule
import ru.dimagor555.synchronization.usecase.di.synchronizationUseCaseModule
import ru.dimagor555.synchronization.usecase.rest.api.SendPasswordApi
import ru.dimagor555.synchronization.usecase.rest.repository.ClientRepository
import ru.dimagor555.synchronization.usecase.rest.repository.ReceivePasswordRepository
import ru.dimagor555.synchronization.usecase.rest.repository.SendPasswordRepository
import ru.dimagor555.synchronization.usecase.rest.repository.ServerRepository
import ru.dimagor555.synchronization.usecase.syncdevice.api.SyncDeviceApi
import ru.dimagor555.synchronization.usecase.syncdevice.api.SyncDeviceRepository
import ru.dimagor555.synchronization.usecase.syncresult.repository.SyncResultRepository
import ru.dimagor555.synchronization.usecase.syncstatus.repository.SyncStatusRepository

val synchronizationModule = module {
    single { ConnectionAddress() }
    singleOf(::ServerRepositoryImpl) bind ServerRepository::class
    singleOf(::ClientRepositoryImpl) bind ClientRepository::class

    singleOf(::SendPasswordApiImpl) bind SendPasswordApi::class

    singleOf(::SyncDeviceApiImpl) bind SyncDeviceApi::class
    singleOf(::SyncDeviceRepositoryImpl) bind SyncDeviceRepository::class

    singleOf(::SendPasswordRepositoryImpl) bind SendPasswordRepository::class
    singleOf(::ReceivePasswordRepositoryImpl) bind ReceivePasswordRepository::class

    singleOf(::SyncResultRepositoryImpl) bind SyncResultRepository::class
    singleOf(::SyncStatusRepositoryImpl) bind SyncStatusRepository::class

    includes(synchronizationUseCaseModule)
    includes(synchronizationUiModule)
}
