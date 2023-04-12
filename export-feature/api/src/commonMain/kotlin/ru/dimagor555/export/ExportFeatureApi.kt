package ru.dimagor555.export

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import ru.dimagor555.export.domain.SaveExportToFileResult
import ru.dimagor555.export.ui.exportscreen.ExportComponent
import ru.dimagor555.export.ui.importscreen.ImportComponent

interface ExportFeatureApi {

    suspend fun saveExportToFile(fileUri: String): SaveExportToFileResult

    fun createExportComponent(
        componentContext: ComponentContext,
        onNavigateBack: () -> Unit,
    ): ExportComponent

    @Composable
    fun ExportScreen(component: ExportComponent)

    fun createImportComponent(
        componentContext: ComponentContext,
        onNavigateBack: () -> Unit,
    ): ImportComponent

    @Composable
    fun ImportScreen(component: ImportComponent)
}