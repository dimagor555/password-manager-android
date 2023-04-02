package ru.dimagor555.synchronization.ui.resultsyncscreen.store

import ru.dimagor555.mvicompose.abstraction.Reducer
import ru.dimagor555.synchronization.ui.resultsyncscreen.store.ResultSyncStore.Message
import ru.dimagor555.synchronization.ui.resultsyncscreen.store.ResultSyncStore.State

class ResultSyncReducer : Reducer<State, Message> {

    override fun State.reduce(msg: Message): State =
        when (msg) {
            is Message.UpdateSyncSuccess -> copy(isSyncSuccess = msg.isSyncSuccess)
            is Message.ShowSyncResult -> copy(syncResult = msg.syncResult)
            is Message.ExitScreen -> copy(isExitScreen = true)
        }
}