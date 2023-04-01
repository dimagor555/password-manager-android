package ru.dimagor555.synchronization.ui.deviceslistscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ru.dimagor555.res.core.MR
import ru.dimagor555.synchronization.ui.deviceslistscreen.store.DevicesListStore.Action
import ru.dimagor555.synchronization.ui.deviceslistscreen.view.DevicesListScreenContent
import ru.dimagor555.synchronization.ui.deviceslistscreen.view.SyncTopAppBar
import ru.dimagor555.ui.core.component.SingleSnackbarScaffold
import ru.dimagor555.ui.core.util.stringResource

@Composable
fun DevicesListScreen(component: DevicesListComponent) {
    component as DevicesListComponentImpl

    val state by component.state.collectAsState()

    LaunchedEffect(key1 = state.isExitScreen) {
        if (state.isExitScreen) {
            component.callbacks.navigateBack()
        }
    }

    SingleSnackbarScaffold(
        topBar = {
            SyncTopAppBar(
                title = stringResource(MR.strings.synchronization),
                onNavigateBack = { component.sendAction(Action.ExitScreen) },
            )
        }
    ) { _, onShowSnackbar ->
        DevicesListScreenContent(
            state = state,
            navigateToSyncScreen = {
                component.sendAction(Action.SelectSyncDevice(it))
                component.callbacks.navigateToSyncScreen(true)
            },
        )
    }

    LaunchedEffect(key1 = state.isSyncStart) {
        if (state.isSyncStart) {
            component.callbacks.navigateToSyncScreen(false)
        }
    }
}