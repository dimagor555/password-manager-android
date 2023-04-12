package ru.dimagor555.export

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import ru.dimagor555.export.domain.SaveExportToFileResult
import ru.dimagor555.export.ui.exportscreen.ExportComponent
import ru.dimagor555.export.ui.exportscreen.ExportComponentImpl
import ru.dimagor555.export.ui.importscreen.ImportComponent
import ru.dimagor555.export.ui.importscreen.ImportComponentImpl
import ru.dimagor555.export.usecase.SaveExportToFileUsecase
import ru.dimagor555.export.ui.exportscreen.ExportScreen as ExportScreenImpl
import ru.dimagor555.export.ui.importscreen.ImportScreen as ImportScreenImpl

internal class ExportFeatureApiImpl(
    private val saveExportToFile: SaveExportToFileUsecase,
) : ExportFeatureApi {

    override suspend fun saveExportToFile(fileUri: String): SaveExportToFileResult =
        saveExportToFile(SaveExportToFileUsecase.Params(fileUri))

    override fun createExportComponent(
        componentContext: ComponentContext,
        onNavigateBack: () -> Unit,
    ): ExportComponent = ExportComponentImpl(
        componentContext = componentContext,
        onNavigateBack = onNavigateBack,
    )

    @Composable
    override fun ExportScreen(component: ExportComponent) =
        ExportScreenImpl(component)

    override fun createImportComponent(
        componentContext: ComponentContext,
        onNavigateBack: () -> Unit,
    ): ImportComponent = ImportComponentImpl(
        componentContext = componentContext,
        onNavigateBack = onNavigateBack,
    )

    @Composable
    override fun ImportScreen(component: ImportComponent) =
        ImportScreenImpl(component)
}