package ru.dimagor555.synchronization.ui.syncscreen.store

import ru.dimagor555.mvicompose.abstraction.Store
import ru.dimagor555.mvicompose.implementation.SimpleActionBootstrapper
import ru.dimagor555.mvicompose.implementation.StoreImpl
import ru.dimagor555.synchronization.domain.syncstatus.SyncStatus
import ru.dimagor555.synchronization.ui.deviceslistscreen.store.SyncDeviceState
import ru.dimagor555.synchronization.ui.syncscreen.store.SyncStore.Action
import ru.dimagor555.synchronization.ui.syncscreen.store.SyncStore.State

class SyncStore(isClient: Boolean) : Store<Action, State, Nothing> by StoreImpl(
    initialState = State(),
    actor = SyncActor(),
    reducer = SyncReducer(),
    bootstrapper = SimpleActionBootstrapper(Action.StartSync(isClient)),
) {
    data class State(
        val id: String? = null,
        val canStopped: Boolean = true,
        val selectedSyncDeviceState: SyncDeviceState? = null,
        val syncStatus: SyncStatus = SyncStatus.Initial,
        val isExitScreen: Boolean = false,
    )

    sealed interface Action {
        data class StartSync(val isClient: Boolean) : Action

        object StopSync : Action
    }

    sealed interface Message {
        data class UpdateExitScreen(val isExitScreen: Boolean) : Message

        data class UpdateSyncStatus(val syncStatus: SyncStatus) : Message
    }
}