package ru.dimagor555.export.ui.exportscreen

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.*
import ru.dimagor555.export.ui.exportscreen.composable.ExportFilePathChooser
import ru.dimagor555.export.ui.exportscreen.composable.ExportScaffold
import ru.dimagor555.export.ui.exportscreen.composable.ExportScreenContent
import ru.dimagor555.export.ui.exportscreen.store.ExportStore
import ru.dimagor555.export.ui.exportscreen.store.ExportStore.Action
import ru.dimagor555.export.ui.exportscreen.store.ExportStore.State
import ru.dimagor555.ui.core.theme.PasswordManagerTheme
import ru.dimagor555.ui.core.util.OnSideEffect
import ru.dimagor555.ui.core.util.createLongSnackbarMessage

@Composable
fun ExportScreen(component: ExportComponent) {
    component as ExportComponentImpl

    val state by component.state.collectAsState()

    ExportScaffold(
        onNavigateBack = { component.onNavigateBack() },
    ) { snackbarHostState ->
        ExportScreenContent(
            state = state,
            sendAction = component::sendAction,
        )
        ExportOnSideEffect(
            component = component,
            snackbarHostState = snackbarHostState,
        )
    }
    LaunchedEffect(state.isExitScreen) {
        if (state.isExitScreen) {
            component.onNavigateBack()
        }
    }
}

@Composable
internal fun ExportOnSideEffect(
    component: ExportComponentImpl,
    snackbarHostState: SnackbarHostState,
) {
    var exportFileName by remember { mutableStateOf<String?>(null) }
    ExportFilePathChooser(
        fileName = exportFileName,
        onResetFileName = { exportFileName = null },
        onSuccess = { component.sendAction(Action.OnChooseFilePathSuccess(it)) },
        onFailure = { component.sendAction(Action.OnChooseFilePathFailure) },
    )
    OnSideEffect(
        component = component,
        snackbarHostState = snackbarHostState,
        onSideEffect = { sideEffect, showSnackbar ->
            when (sideEffect) {
                is ExportStore.SideEffect.ShowMessage -> showSnackbar(
                    createLongSnackbarMessage(sideEffect.message)
                )

                is ExportStore.SideEffect.OpenFilePathChooser -> {
                    exportFileName = sideEffect.fileName
                }
            }
        }
    )
}

@Preview
@Composable
private fun ExportScreenPreview() {
    PasswordManagerTheme {
        ExportScaffold(
            onNavigateBack = {},
        ) {
            ExportScreenContent(
                state = State(),
                sendAction = {},
            )
        }
    }
}