package ru.dimagor555.export.ui.importscreen.composable

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import ru.dimagor555.ui.core.component.SingleSnackbarScaffold

@Composable
internal fun ImportScaffold(
    onNavigateBack: () -> Unit,
    content: @Composable (snackbarHostState: SnackbarHostState) -> Unit
) {
    SingleSnackbarScaffold(
        topBar = {
            ImportTopAppBar(onNavigateBack = onNavigateBack)
        },
    ) { _, onShowSnackbar ->
        content(onShowSnackbar)
    }
}