package ru.dimagor555.export.ui.importscreen.store

import ru.dimagor555.export.ui.importscreen.store.ImportStore.Message
import ru.dimagor555.export.ui.importscreen.store.ImportStore.State
import ru.dimagor555.mvicompose.abstraction.Reducer

internal class ImportReducer : Reducer<State, Message> {

    override fun State.reduce(msg: Message): State =
        when (msg) {
            is Message.ShowSelectedFile -> copy(
                fileState = State.FileState.Selected(
                    uri = msg.uri,
                    pathOrName = msg.pathOrName,
                ),
                error = null,
            )

            is Message.ShowError -> copy(
                error = msg.error,
            )

            is Message.ToggleClearBeforeImport -> copy(
                isClearBeforeImport = !isClearBeforeImport,
            )

            is Message.ToggleMakeBackup -> copy(
                isMakeBackup = !isMakeBackup,
            )

            is Message.StartFileChoosing -> copy(
                currentTask = State.Task.FileChoosing,
            )

            is Message.StartImport -> copy(
                currentTask = State.Task.Importing,
                error = null,
            )

            is Message.FinishFileChoosing, is Message.FinishImport -> copy(
                currentTask = State.Task.FormFilling,
            )

            is Message.ExitScreen -> copy(
                currentTask = State.Task.ExitingScreen,
            )
        }
}