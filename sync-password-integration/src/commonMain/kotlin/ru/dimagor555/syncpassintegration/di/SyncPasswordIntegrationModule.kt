package ru.dimagor555.syncpassintegration.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.dimagor555.synchronization.usecase.syncpassword.repository.SyncPasswordRepository
import ru.dimagor555.syncpassintegration.data.SyncPasswordRepositoryImpl
import ru.dimagor555.syncpassintegration.usecase.FilterPasswordAndFolderChildrenUseCase

val syncPasswordIntegrationModule = module {
    singleOf(::SyncPasswordRepositoryImpl) bind SyncPasswordRepository::class

    factoryOf(::FilterPasswordAndFolderChildrenUseCase)
}
