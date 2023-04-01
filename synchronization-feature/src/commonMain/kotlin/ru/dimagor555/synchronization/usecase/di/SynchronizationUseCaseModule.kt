package ru.dimagor555.synchronization.usecase.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.dimagor555.synchronization.usecase.syncpassword.AddOrUpdatePasswordsAndFolderChildrenUseCase
import ru.dimagor555.synchronization.usecase.syncpassword.GetSyncResponseUseCase
import ru.dimagor555.synchronization.usecase.syncresult.UpdateSyncResultUseCase
import ru.dimagor555.synchronization.usecase.syncstatus.SetSyncStatusUseCase

val synchronizationUseCaseModule = module {
    factoryOf(::GetSyncResponseUseCase)
    factoryOf(::AddOrUpdatePasswordsAndFolderChildrenUseCase)

    factoryOf(::SetSyncStatusUseCase)
    factoryOf(::UpdateSyncResultUseCase)
}