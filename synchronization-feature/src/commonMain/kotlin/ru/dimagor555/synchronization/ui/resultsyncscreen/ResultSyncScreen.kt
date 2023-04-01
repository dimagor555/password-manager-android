package ru.dimagor555.synchronization.ui.resultsyncscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ru.dimagor555.res.core.MR
import ru.dimagor555.synchronization.ui.deviceslistscreen.view.SyncTopAppBar
import ru.dimagor555.synchronization.ui.resultsyncscreen.view.ResultSyncScreenContent
import ru.dimagor555.ui.core.component.SingleSnackbarScaffold
import ru.dimagor555.ui.core.util.stringResource

@Composable
fun ResultSyncScreen(component: ResultSyncComponent) {
    component as ResultSyncComponentImpl

    val state by component.state.collectAsState()

    SingleSnackbarScaffold(
        topBar = {
            SyncTopAppBar(
                title = stringResource(MR.strings.synchronization),
                onNavigateBack = component.callbacks.navigateToSettingsScreen,
            )
        }
    ) { _, onShowSnackbar ->
        ResultSyncScreenContent(
            isSyncSuccess = state.isSyncSuccess,
            syncResult = state.syncResult,
            onOkButtonClick = {
                when (state.isSyncSuccess) {
                    true -> component.callbacks.navigateToPasswordsListScreen()
                    false -> component.callbacks.navigateToDevicesListScreen()
                }
            },
        )
    }
}