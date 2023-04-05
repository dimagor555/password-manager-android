package ru.dimagor555.export.ui.exportscreen.composable

import androidx.compose.runtime.Composable

@Composable
internal fun ExportFilePathChooser(
    fileName: String?,
    onResetFileName: () -> Unit,
    onSuccess: (String) -> Unit,
    onFailure: () -> Unit,
) {
    ExportFilePathChooser(
        fileName = fileName,
        onSuccess = {
            onResetFileName()
            onSuccess(it)
        },
        onFailure = {
            onResetFileName()
            onFailure()
        },
    )
}

@Composable
internal expect fun ExportFilePathChooser(
    fileName: String?,
    onSuccess: (String) -> Unit,
    onFailure: () -> Unit,
)