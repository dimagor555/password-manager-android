package ru.dimagor555.export.ui.importscreen.composable

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
internal actual fun ImportFileChooser(
    isVisible: Boolean,
    onSuccess: (String) -> Unit,
    onFailure: () -> Unit
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { fileUri ->
            when (fileUri) {
                null -> onFailure()
                else -> onSuccess(fileUri.toString())
            }
        },
    )
    LaunchedEffect(isVisible) {
        if (isVisible.not()) return@LaunchedEffect
        launcher.launch(arrayOf("*/*"))
    }
}