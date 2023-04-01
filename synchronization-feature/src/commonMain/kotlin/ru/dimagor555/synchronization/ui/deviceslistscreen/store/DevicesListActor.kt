package ru.dimagor555.synchronization.ui.deviceslistscreen.store

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.dimagor555.mvicompose.abstraction.Actor
import ru.dimagor555.synchronization.ui.deviceslistscreen.store.DevicesListStore.*

internal class DevicesListActor : Actor<State, Action, Message, Nothing>(), KoinComponent {

    private val useCases: DevicesListUseCases by inject()

    override suspend fun onAction(action: Action) {
        when (action) {
            is Action.SelectSyncDevice -> selectSyncDevice(action.syncDeviceState)
            is Action.ExitScreen -> exitScreen()
        }
    }

    private suspend fun selectSyncDevice(syncDeviceState: SyncDeviceState) = coroutineScope {
        useCases.setConnectionAddress(syncDeviceState)
        stopServer()
    }

    private fun CoroutineScope.stopServer() = launch {
        useCases.stopServer()
    }

    private suspend fun exitScreen() {
        useCases.stopServer()
        sendMessage(Message.ExitScreen)
    }
}