package ru.dimagor555.synchronization.ui.deviceslistscreen.store

import ru.dimagor555.mvicompose.abstraction.Reducer
import ru.dimagor555.synchronization.ui.deviceslistscreen.store.DevicesListStore.Message
import ru.dimagor555.synchronization.ui.deviceslistscreen.store.DevicesListStore.State

internal class DevicesListReducer : Reducer<State, Message> {

    override fun State.reduce(msg: Message): State =
        when (msg) {
            is Message.ShowLoading -> copy(isLoading = msg.isLoading)
            is Message.StartSync -> copy(isSyncStart = true)
            is Message.ShowSyncDevices -> copy(syncDeviceStates = msg.syncDeviceStates)
            is Message.ExitScreen -> copy(isExitScreen = true)
        }
}