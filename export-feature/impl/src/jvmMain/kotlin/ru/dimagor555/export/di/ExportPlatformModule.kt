package ru.dimagor555.export.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.dimagor555.export.data.StorageRepositoryImpl
import ru.dimagor555.export.usecase.repository.StorageRepository

internal actual val exportPlatformModule = module {
    factoryOf(::StorageRepositoryImpl) bind StorageRepository::class
}