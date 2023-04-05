package ru.dimagor555.export.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.dimagor555.export.ExportFeatureApi
import ru.dimagor555.export.ExportFeatureApiImpl
import ru.dimagor555.export.ui.exportscreen.store.ExportUsecases
import ru.dimagor555.export.ui.importscreen.store.ImportUsecases
import ru.dimagor555.export.usecase.*

val exportModule = module {
    includes(exportPlatformModule)

    singleOf(::ExportFeatureApiImpl) bind ExportFeatureApi::class

    factoryOf(::ExportUsecases)
    factoryOf(::ImportUsecases)

    factoryOf(::BuildExportFileNameUsecase)
    factoryOf(::SaveExportToFileUsecase)
    factoryOf(::LoadExportFromFileUsecase)
    factoryOf(::CollectExportUsecase)
    factoryOf(::ReadExportFromFileUsecase)
    factoryOf(::GetFilePathOrNameUsecase)
}