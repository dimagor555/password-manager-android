package ru.dimagor555.synchronization.ui.syncscreen.store

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.dimagor555.mvicompose.abstraction.Actor
import ru.dimagor555.synchronization.domain.syncstatus.SyncStatus
import ru.dimagor555.synchronization.ui.syncscreen.store.SyncStore.*

class SyncActor : Actor<State, Action, Message, Nothing>(), KoinComponent {

    private val useCases: SyncUseCases by inject()

    override suspend fun onAction(action: Action) {
        when (action) {
            is Action.StartSync -> startSync(action.isClient)
            is Action.StopSync -> stopSync()
        }
    }

    private suspend fun startSync(isClient: Boolean) {
        if (isClient) {
            useCases.postSyncPasswordRecord()
        }
        useCases.observeSyncStatus().collect {
            sendMessage(Message.UpdateSyncStatus(it))
            when (it) {
                SyncStatus.SuccessSync -> sendMessage(Message.UpdateExitScreen(true))
                else -> {}
            }
        }
    }

    private suspend fun stopSync() {
        useCases.stopServer()
        useCases.closeClient()
        useCases.clearConnectionAddress()
        sendMessage(Message.UpdateExitScreen(true))
    }
}