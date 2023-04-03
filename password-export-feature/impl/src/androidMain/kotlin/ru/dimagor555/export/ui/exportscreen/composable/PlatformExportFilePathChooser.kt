package ru.dimagor555.export.ui.exportscreen.composable

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
internal actual fun ExportFilePathChooser(
    fileName: String?,
    onSuccess: (String) -> Unit,
    onFailure: () -> Unit,
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("*/*"),
        onResult = { fileUri ->
            when (fileUri) {
                null -> onFailure()
                else -> onSuccess(fileUri.toString())
            }
        },
    )
    LaunchedEffect(fileName) {
        fileName ?: return@LaunchedEffect
        launcher.launch(fileName)
    }
}