package ru.dimagor555.export.ui.importscreen

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.*
import ru.dimagor555.export.ui.importscreen.composable.ImportFileChooser
import ru.dimagor555.export.ui.importscreen.composable.ImportScaffold
import ru.dimagor555.export.ui.importscreen.composable.ImportScreenContent
import ru.dimagor555.export.ui.importscreen.store.ImportStore.*
import ru.dimagor555.export.ui.importscreen.store.ImportStore.State
import ru.dimagor555.ui.core.theme.PasswordManagerTheme
import ru.dimagor555.ui.core.util.OnSideEffect
import ru.dimagor555.ui.core.util.createLongSnackbarMessage

@Composable
fun ImportScreen(component: ImportComponent) {
    component as ImportComponentImpl

    val state by component.state.collectAsState()

    ImportScaffold(
        onNavigateBack = { component.onNavigateBack() },
    ) { snackbarHostState ->
        ImportScreenContent(
            state = state,
            sendAction = component::sendAction,
        )
        ImportOnSideEffect(
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
internal fun ImportOnSideEffect(
    component: ImportComponentImpl,
    snackbarHostState: SnackbarHostState,
) {
    var isFileChooserVisible by remember { mutableStateOf(false) }
    ImportFileChooser(
        isVisible = isFileChooserVisible,
        onClose = { isFileChooserVisible = false },
        onSuccess = { component.sendAction(Action.OnChooseFileSuccess(it)) },
        onFailure = { component.sendAction(Action.OnChooseFileFailure) },
    )
    OnSideEffect(
        component = component,
        snackbarHostState = snackbarHostState,
        onSideEffect = { sideEffect, showSnackbar ->
            when (sideEffect) {
                is SideEffect.ShowMessage -> showSnackbar(
                    createLongSnackbarMessage(sideEffect.message)
                )

                is SideEffect.OpenFileChooser -> {
                    isFileChooserVisible = true
                }
            }
        }
    )
}

@Preview
@Composable
private fun ExportScreenPreview() {
    PasswordManagerTheme {
        ImportScaffold(
            onNavigateBack = {},
        ) {
            ImportScreenContent(
                state = State(),
                sendAction = {},
            )
        }
    }
}