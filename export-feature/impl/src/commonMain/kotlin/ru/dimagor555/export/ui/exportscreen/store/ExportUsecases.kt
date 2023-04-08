package ru.dimagor555.export.ui.exportscreen.store

import ru.dimagor555.export.usecase.BuildExportFileNameUsecase
import ru.dimagor555.export.usecase.SaveExportToFileUsecase

internal class ExportUsecases(
    val buildExportFileName: BuildExportFileNameUsecase,
    val saveExportToFile: SaveExportToFileUsecase,
)