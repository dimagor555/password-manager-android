package ru.dimagor555.synchronization.ui.resultsyncscreen.store

import ru.dimagor555.mvicompose.abstraction.Store
import ru.dimagor555.mvicompose.implementation.SimpleActionBootstrapper
import ru.dimagor555.mvicompose.implementation.StoreImpl
import ru.dimagor555.synchronization.domain.syncresult.SyncResult
import ru.dimagor555.synchronization.ui.resultsyncscreen.store.ResultSyncStore.Action
import ru.dimagor555.synchronization.ui.resultsyncscreen.store.ResultSyncStore.Action.SetSyncResult
import ru.dimagor555.synchronization.ui.resultsyncscreen.store.ResultSyncStore.State

class ResultSyncStore(private val isSyncSuccess: Boolean) : Store<Action, State, Nothing> by StoreImpl(
    initialState = State(),
    actor = ResultSyncActor(),
    reducer = ResultSyncReducer(),
    bootstrapper = SimpleActionBootstrapper(SetSyncResult(isSyncSuccess))
) {
    data class State(
        val isSyncSuccess: Boolean = false,
        val syncResult: SyncResult = SyncResult(),
        val isExitScreen: Boolean = false,
    )

    sealed interface Action {
        data class SetSyncResult(val isSyncSuccess: Boolean) : Action
        object ExitScreen : Action
    }

    sealed interface Message {
        data class UpdateSyncSuccess(val isSyncSuccess: Boolean) : Message
        data class ShowSyncResult(val syncResult: SyncResult) : Message
        object ExitScreen : Message
    }
}