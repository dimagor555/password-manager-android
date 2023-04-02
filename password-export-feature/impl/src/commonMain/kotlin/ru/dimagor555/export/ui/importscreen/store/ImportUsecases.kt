package ru.dimagor555.export.ui.importscreen.store

import ru.dimagor555.export.usecase.GetFilePathOrNameUsecase
import ru.dimagor555.export.usecase.LoadExportFromFileUsecase

internal class ImportUsecases(
    val loadExportFromFile: LoadExportFromFileUsecase,
    val getFilePathOrName: GetFilePathOrNameUsecase,
)