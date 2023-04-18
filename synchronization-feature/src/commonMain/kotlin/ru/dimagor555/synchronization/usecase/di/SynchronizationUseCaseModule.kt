package ru.dimagor555.synchronization.usecase.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.dimagor555.synchronization.usecase.ServerSyncUseCase
import ru.dimagor555.synchronization.usecase.rest.ReceiveEncryptedInitialSyncRequestUsecase
import ru.dimagor555.synchronization.usecase.rest.SendEncryptedRequestPasswordsUsecase
import ru.dimagor555.synchronization.usecase.rest.SendEncryptedSyncPasswordRecordsUsecase
import ru.dimagor555.synchronization.usecase.syncpassword.AddOrUpdatePasswordsAndFolderChildrenUseCase
import ru.dimagor555.synchronization.usecase.syncpassword.GetSyncResponseUseCase
import ru.dimagor555.synchronization.usecase.syncresult.UpdateSyncResultUseCase
import ru.dimagor555.synchronization.usecase.syncstatus.SetSyncStatusUseCase

val synchronizationUseCaseModule = module {
    factoryOf(::GetSyncResponseUseCase)

    factoryOf(::SendEncryptedSyncPasswordRecordsUsecase)
    factoryOf(::SendEncryptedRequestPasswordsUsecase)
    factoryOf(::AddOrUpdatePasswordsAndFolderChildrenUseCase)
    factoryOf(::ReceiveEncryptedInitialSyncRequestUsecase)

    factoryOf(::SetSyncStatusUseCase)
    factoryOf(::UpdateSyncResultUseCase)

    singleOf(::ServerSyncUseCase)
}