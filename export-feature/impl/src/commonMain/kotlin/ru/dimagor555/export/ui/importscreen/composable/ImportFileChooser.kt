package ru.dimagor555.export.ui.importscreen.composable

import androidx.compose.runtime.Composable

@Composable
internal fun ImportFileChooser(
    isVisible: Boolean,
    onClose: () -> Unit,
    onSuccess: (String) -> Unit,
    onFailure: () -> Unit,
) {
    ImportFileChooser(
        isVisible = isVisible,
        onSuccess = {
            onClose()
            onSuccess(it)
        },
        onFailure = {
            onClose()
            onFailure()
        },
    )
}

@Composable
internal expect fun ImportFileChooser(
    isVisible: Boolean,
    onSuccess: (String) -> Unit,
    onFailure: () -> Unit,
)
