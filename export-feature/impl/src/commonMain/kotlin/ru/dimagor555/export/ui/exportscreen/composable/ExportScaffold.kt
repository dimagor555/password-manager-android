package ru.dimagor555.export.ui.exportscreen.composable

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import ru.dimagor555.ui.core.component.SingleSnackbarScaffold

@Composable
internal fun ExportScaffold(
    onNavigateBack: () -> Unit,
    content: @Composable (snackbarHostState: SnackbarHostState) -> Unit
) {
    SingleSnackbarScaffold(
        topBar = {
            ExportTopAppBar(onNavigateBack = onNavigateBack)
        },
    ) { _, onShowSnackbar ->
        content(onShowSnackbar)
    }
}