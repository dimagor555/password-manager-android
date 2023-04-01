package ru.dimagor555.synchronization.ui.deviceslistscreen.store

import ru.dimagor555.mvicompose.abstraction.Store
import ru.dimagor555.mvicompose.implementation.StoreImpl
import ru.dimagor555.synchronization.ui.deviceslistscreen.store.DevicesListStore.Action
import ru.dimagor555.synchronization.ui.deviceslistscreen.store.DevicesListStore.State

internal class DevicesListStore : Store<Action, State, Nothing> by StoreImpl(
    initialState = State(),
    actor = DevicesListActor(),
    reducer = DevicesListReducer(),
    bootstrapper = DevicesListBootstrapper(),
) {

    data class State(
        val isLoading: Boolean = true,
        val isSyncStart: Boolean = false,
        val syncDeviceStates: List<SyncDeviceState> = emptyList(),
        val isExitScreen: Boolean = false,
    ) {
        val hasNoSyncDevices
            get() = syncDeviceStates.isEmpty()
    }

    sealed interface Action {

        data class SelectSyncDevice(val syncDeviceState: SyncDeviceState): Action

        object ExitScreen : Action
    }

    sealed interface Message {
        data class ShowLoading(val isLoading: Boolean) : Message

        object StartSync : Message

        data class ShowSyncDevices(val syncDeviceStates: List<SyncDeviceState>) : Message

        object ExitScreen : Message
    }
}
