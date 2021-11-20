package ru.dimagor555.ui.core.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

@Composable
fun SingleSnackbarSimpleScaffold(
    modifier: Modifier = Modifier,
    topAppBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.Center,
    content: @Composable (PaddingValues, onShowSnackbar: (String, String?) -> Unit) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        topBar = topAppBar,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition
    ) {
        val onShowSnackbar = { message: String, actionLabel: String? ->
            coroutineScope.launch {
                scaffoldState.snackbarHostState.showSingleSnackbar(message, actionLabel)
            }
        }
        content(it, onShowSnackbar)
    }
}

private suspend fun SnackbarHostState.showSingleSnackbar(message: String, actionLabel: String?) {
    currentSnackbarData?.dismiss()
    showSnackbar(message, actionLabel)
}