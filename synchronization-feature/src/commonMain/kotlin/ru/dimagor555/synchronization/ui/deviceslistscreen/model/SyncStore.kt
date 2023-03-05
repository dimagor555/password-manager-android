package ru.dimagor555.synchronization.ui.deviceslistscreen.model

import ru.dimagor555.mvicompose.abstraction.Store
import ru.dimagor555.mvicompose.implementation.StoreImpl
import ru.dimagor555.synchronization.domain.SyncDevice
import ru.dimagor555.synchronization.ui.deviceslistscreen.model.SyncStore.*


/*
При нажатии на кнопку "Старт", идет сканирование сети на наличие серверов, доступных к подключению
Если находим сервер, то подключаемся к нему
Если сервера нет, то запускаем свой, и продолжаем сканировать сеть с каким-то промежутком вермени
Если находим - подключаемся, если нас находят - подключаются к нам
Если к нам подключились быстрее при одновременном нажатии, то необходимо выключить сервер на другом
устройстве

сделать как-то постоянный чекер состояния соединения

 */
internal class SyncStore : Store<Action, State, SideEffect> by StoreImpl(
    initialState = State(),
    actor = SyncActor(),
    reducer = SyncReducer(),
//    bootstrapper = SimpleActionBootstrapper(Action.InitScreen),
) {

    data class State(
//        val isServerStart: Boolean = true,
//        val isClientWork: Boolean = true,
//        val isSynchronize: Boolean = true,
        val selectedSyncDevice: SyncDevice? = null,
        val syncDevices: List<SyncDevice> = emptyList(),
        val syncStatus: SyncStatus? = null,
//        val id: String = Folder.ROOT_FOLDER_ID,
//        val passwordStates: List<PasswordState> = emptyList(),
//        val filterState: FilterState = FilterState(),
//        val isSortingDialogVisible: Boolean = false,
    )

    sealed interface Action {

        object InitScreen : Action

        data class SelectSyncDevice(val syncDevice: SyncDevice): Action

        object StartSync : Action

        object StopSync: Action
    }

    sealed interface Message {
        data class ShowLoading(val isSynchronize: Boolean) : Message

        data class ShowSyncDevices(val syncDevices: List<SyncDevice>) : Message

        data class ShowSelectedSyncDevice(val syncDevice: SyncDevice) : Message

        data class ShowSyncStatus(val isSuccess: SyncStatus): Message
    }

    sealed interface SideEffect {

    }

}
