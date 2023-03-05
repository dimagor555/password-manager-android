package ru.dimagor555.synchronization.ui.deviceslistscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ru.dimagor555.synchronization.ui.deviceslistscreen.components.SyncScreenContent
import ru.dimagor555.synchronization.ui.deviceslistscreen.components.SyncTopAppBar
import ru.dimagor555.ui.core.component.SingleSnackbarScaffold

@Composable
fun PasswordListScreen(component: SyncComponent) {
    component as SyncComponentImpl

    val state by component.state.collectAsState()

    SingleSnackbarScaffold(
        topBar = {
            SyncTopAppBar(
                title = "stringResource(MR.strings.edit)",
                onNavigateBack = {},
            )
        }
    ) { _, onShowSnackbar ->
        SyncScreenContent(
            state = state,
            sendAction = component::sendAction,
        )
    }
}