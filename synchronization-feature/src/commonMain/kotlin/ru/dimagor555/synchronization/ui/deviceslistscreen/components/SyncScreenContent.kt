package ru.dimagor555.synchronization.ui.deviceslistscreen.components

import androidx.compose.runtime.Composable
import ru.dimagor555.synchronization.ui.deviceslistscreen.model.SyncStore.Action
import ru.dimagor555.synchronization.ui.deviceslistscreen.model.SyncStore.State

@Composable
internal fun SyncScreenContent(
    state: State,
//    onSyncStartClick: () -> Unit,
//    onSyncDeviceChange: () -> Unit,
    sendAction: (Action) -> Unit,
) {

}