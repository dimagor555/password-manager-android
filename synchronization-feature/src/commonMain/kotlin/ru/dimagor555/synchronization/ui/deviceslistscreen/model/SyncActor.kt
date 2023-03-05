package ru.dimagor555.synchronization.ui.deviceslistscreen.model

import io.ktor.http.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.dimagor555.mvicompose.abstraction.Actor
import ru.dimagor555.synchronization.domain.SyncDevice
import ru.dimagor555.synchronization.ui.deviceslistscreen.model.SyncStatus.ERROR
import ru.dimagor555.synchronization.ui.deviceslistscreen.model.SyncStatus.SUCCESS
import ru.dimagor555.synchronization.ui.deviceslistscreen.model.SyncStore.*

internal class SyncActor : Actor<State, Action, Message, SideEffect>(), KoinComponent {

    private val useCases: SyncUseCases by inject()

    override suspend fun onAction(action: Action) {
        when (action) {
            Action.InitScreen -> initScreen()
            is Action.SelectSyncDevice -> selectSyncDevice(action.syncDevice)
            Action.StartSync -> startSync()
            Action.StopSync -> stopSync()
        }
    }

    private suspend fun initScreen() {
        val syncDevices = useCases.findSyncDevicesUseCase()
        sendMessage(Message.ShowSyncDevices(syncDevices))
    }

    private suspend fun selectSyncDevice(syncDevice: SyncDevice) {
        sendMessage(Message.ShowSelectedSyncDevice(syncDevice))
    }

    private suspend fun startSync() {
        //если есть выбранное устройство, то подключаться к нему, а если его нету, то включить сервак у себя

        if (getState().selectedSyncDevice != null) {
            val syncStatusCode = useCases.postSyncPasswordRecordUseCase(
                getState().selectedSyncDevice!!
            )
            val isSuccess = if (syncStatusCode == HttpStatusCode.OK) SUCCESS else ERROR
            sendMessage(Message.ShowSyncStatus(isSuccess))
        } else {
            useCases.startServerUseCase()
        }
    }

    private fun stopSync() {
        useCases.stopServerUseCase()
    }
}