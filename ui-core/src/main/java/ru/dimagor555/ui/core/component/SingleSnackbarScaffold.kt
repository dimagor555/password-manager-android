package ru.dimagor555.ui.core.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import ru.dimagor555.ui.core.util.SnackbarMessage
import ru.dimagor555.ui.core.util.showSingleSnackbar

@Composable
fun SingleSnackbarScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.Center,
    content: @Composable (PaddingValues, onShowSnackbar: (SnackbarMessage) -> Unit) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        topBar = topBar,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition
    ) {
        val onShowSnackbar = { snackbarMessage: SnackbarMessage ->
            coroutineScope.launch {
                scaffoldState.snackbarHostState.showSingleSnackbar(snackbarMessage)
            }
        }
        content(it, onShowSnackbar)
    }
}