package ru.dimagor555.synchronization.ui.syncscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ru.dimagor555.res.core.MR
import ru.dimagor555.synchronization.domain.syncstatus.SyncStatus
import ru.dimagor555.synchronization.ui.deviceslistscreen.view.SyncTopAppBar
import ru.dimagor555.synchronization.ui.syncscreen.store.SyncStore
import ru.dimagor555.synchronization.ui.syncscreen.view.SyncScreenContent
import ru.dimagor555.ui.core.component.SingleSnackbarScaffold
import ru.dimagor555.ui.core.util.stringResource

@Composable
fun SyncScreen(component: SyncComponent) {
    component as SyncComponentImpl

    val state by component.state.collectAsState()

    LaunchedEffect(state.isExitScreen) {
        if (state.isExitScreen.not()) {
            return@LaunchedEffect
        }
        when (state.syncStatus) {
            SyncStatus.ErrorSync -> component.callbacks.navigateToResultSyncScreen(false)
            SyncStatus.SuccessSync -> component.callbacks.navigateToResultSyncScreen(true)
            else -> component.callbacks.navigateBack()
        }
    }

    SingleSnackbarScaffold(
        topBar = {
            SyncTopAppBar(
                title = stringResource(MR.strings.synchronization),
                onNavigateBack = { component.sendAction(SyncStore.Action.StopSync) },
            )
        }
    ) { _, onShowSnackbar ->
        SyncScreenContent(
            state = state,
            sendAction = component::sendAction,
        )
    }
}