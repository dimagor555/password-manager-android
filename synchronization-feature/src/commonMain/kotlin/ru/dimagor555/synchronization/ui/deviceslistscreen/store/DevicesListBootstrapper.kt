package ru.dimagor555.synchronization.ui.deviceslistscreen.store

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.dimagor555.mvicompose.abstraction.Bootstrapper
import ru.dimagor555.synchronization.domain.syncstatus.SyncStatus
import ru.dimagor555.synchronization.ui.deviceslistscreen.store.DevicesListStore.Action
import ru.dimagor555.synchronization.ui.deviceslistscreen.store.DevicesListStore.Message

internal class DevicesListBootstrapper : Bootstrapper<Action, Message>(), KoinComponent {

    private val useCases: DevicesListUseCases by inject()

    override fun init(scope: CoroutineScope) {
        scope.startServer()
        scope.observeSyncStatus()
        scope.findSyncDevices()
    }

    private fun CoroutineScope.startServer() = launch(Dispatchers.IO) {
        useCases.startServer()
    }

    private fun CoroutineScope.observeSyncStatus() {
        useCases
            .observeSyncStatus()
            .onEach { syncStatus ->
                if (syncStatus is SyncStatus.ReceivedPasswordsAnalysis) {
                    sendMessage(Message.StartSync)
                }
            }
            .launchIn(this)
    }

    private fun CoroutineScope.findSyncDevices() = launch {
        while (true) {
            val syncDeviceStates = useCases
                .findSyncDevices()
                .toSyncDeviceStates()
            sendMessage(Message.ShowSyncDevices(syncDeviceStates))
            sendMessage(Message.ShowLoading(isLoading = false))
            delay(5000)
        }
    }
}