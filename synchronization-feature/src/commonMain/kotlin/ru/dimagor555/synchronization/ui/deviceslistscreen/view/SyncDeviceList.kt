package ru.dimagor555.synchronization.ui.deviceslistscreen.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.dimagor555.synchronization.ui.deviceslistscreen.store.SyncDeviceState

@Composable
internal fun SyncDeviceList(
    passwordStates: List<SyncDeviceState>,
    navigateToSyncScreen: (state: SyncDeviceState) -> Unit,
) {
    val listState = rememberLazyListState()
    LaunchedEffect(key1 = passwordStates.size) {
        listState.scrollToItem(0)
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = listState,
        contentPadding = PaddingValues(
            start = 8.dp,
            top = 8.dp,
            end = 8.dp,
            bottom = 56.dp + 36.dp,
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(items = passwordStates) {
            DeviceListItem(
                syncDeviceState = it,
                onSyncDeviceSelected = { navigateToSyncScreen(it) },
            )
        }
    }
}