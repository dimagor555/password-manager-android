package ru.dimagor555.synchronization.ui.deviceslistscreen.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhonelinkOff
import androidx.compose.runtime.Composable
import ru.dimagor555.res.core.MR
import ru.dimagor555.synchronization.ui.deviceslistscreen.store.DevicesListStore.State
import ru.dimagor555.synchronization.ui.deviceslistscreen.store.SyncDeviceState
import ru.dimagor555.ui.core.component.FullscreenCircularProgressBar
import ru.dimagor555.ui.core.component.FullscreenInformationContent
import ru.dimagor555.ui.core.util.stringResource

@Composable
internal fun DevicesListScreenContent(
    state: State,
    navigateToSyncScreen: (state: SyncDeviceState) -> Unit,
) {
    when {
        state.isLoading -> FullscreenCircularProgressBar()
        state.hasNoSyncDevices -> NoSyncDevices()
        else -> SyncDeviceListWrapper(
            passwordStates = state.syncDeviceStates,
            navigateToSyncScreen = navigateToSyncScreen,
        )
    }
}

@Composable
private fun NoSyncDevices() {
    FullscreenInformationContent(
        title = stringResource(MR.strings.not_found_sync_devices_title),
        contentText = stringResource(MR.strings.not_found_sync_devices_content),
        image = Icons.Default.PhonelinkOff,
    )
}

@Composable
private fun SyncDeviceListWrapper(
    passwordStates: List<SyncDeviceState>,
    navigateToSyncScreen: (state: SyncDeviceState) -> Unit,
) {
    SyncDeviceList(
        passwordStates = passwordStates,
        navigateToSyncScreen = navigateToSyncScreen,
    )
}