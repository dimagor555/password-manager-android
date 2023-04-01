package ru.dimagor555.synchronization.ui.syncscreen.store

import ru.dimagor555.mvicompose.abstraction.Reducer
import ru.dimagor555.synchronization.ui.syncscreen.store.SyncStore.Message
import ru.dimagor555.synchronization.ui.syncscreen.store.SyncStore.State

class SyncReducer : Reducer<State, Message> {

    override fun State.reduce(msg: Message): State =
        when (msg) {
            is Message.UpdateExitScreen -> copy(isExitScreen = msg.isExitScreen)
            is Message.UpdateSyncStatus -> copy(syncStatus = msg.syncStatus)
        }
}