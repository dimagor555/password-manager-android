package ru.dimagor555.synchronization.ui.deviceslistscreen.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.dimagor555.synchronization.ui.deviceslistscreen.store.SyncDeviceState
import ru.dimagor555.ui.core.component.SingleLineText
import ru.dimagor555.ui.core.util.ProvideMediumAlpha

@Composable
fun DeviceListItem(
    syncDeviceState: SyncDeviceState,
    onSyncDeviceSelected: () -> Unit,
) {
    SyncDeviceCard(
        onSyncDeviceSelected = onSyncDeviceSelected
    ) {
        SyncDeviceContent(
            modifier = Modifier.weight(1f),
            syncDeviceState = syncDeviceState,
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SyncDeviceCard(
    onSyncDeviceSelected: () -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = { onSyncDeviceSelected() },
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            content()
        }
    }
}

@Composable
private fun SyncDeviceContent(
    modifier: Modifier = Modifier,
    syncDeviceState: SyncDeviceState,
) {
    Column(
        modifier = modifier.padding(horizontal = 4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SingleLineText(
            text = syncDeviceState.hostName,
            style = MaterialTheme.typography.subtitle1,
        )
        ProvideMediumAlpha {
            SingleLineText(
                text = syncDeviceState.address,
                style = MaterialTheme.typography.body1,
            )
        }
    }
}
