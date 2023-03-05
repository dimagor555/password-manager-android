package ru.dimagor555.synchronization.ui.deviceslistscreen.model

import ru.dimagor555.mvicompose.abstraction.Reducer
import ru.dimagor555.synchronization.ui.deviceslistscreen.model.SyncStore.*

internal class SyncReducer : Reducer<State, Message> {

    override fun State.reduce(msg: Message): State =
        when (msg) {
            is Message.ShowLoading -> TODO()
            is Message.ShowSyncDevices -> copy(syncDevices = msg.syncDevices)
            is Message.ShowSelectedSyncDevice -> copy(selectedSyncDevice = msg.syncDevice)
            is Message.ShowSyncStatus -> copy(syncStatus = msg.isSuccess)
        }
}