package ru.dimagor555.synchronization.ui.resultsyncscreen.store

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.dimagor555.mvicompose.abstraction.Actor
import ru.dimagor555.synchronization.ui.resultsyncscreen.store.ResultSyncStore.*

class ResultSyncActor : Actor<State, Action, Message, SideEffect>(), KoinComponent {

    private val useCases: ResultSyncUseCases by inject()

    override suspend fun onAction(action: Action) {
        when (action) {
            is Action.SetSyncResult -> setSyncResult(action.isSyncSuccess)
        }
    }

    private suspend fun setSyncResult(isSyncSuccess: Boolean) {
        when (isSyncSuccess) {
            true -> {
                useCases.stopServer()
                sendMessage(Message.UpdateSyncSuccess(true))
                val syncResult = useCases.getSyncResult()
                sendMessage(Message.ShowSyncResult(syncResult))
            }
            false -> {
                sendMessage(Message.UpdateSyncSuccess(false))
            }
        }
    }
}