package ru.dimagor555.export.ui.exportscreen.store

import ru.dimagor555.export.ui.exportscreen.store.ExportStore.Message
import ru.dimagor555.export.ui.exportscreen.store.ExportStore.State
import ru.dimagor555.mvicompose.abstraction.Reducer

internal class ExportReducer : Reducer<State, Message> {

    override fun State.reduce(msg: Message): State =
        when (msg) {
            is Message.ShowFileName -> copy(
                fileName = fileName.copy(
                    text = msg.fileName,
                    error = null,
                ),
            )

            is Message.ShowFileNameError -> copy(
                fileName = fileName.copy(error = msg.error),
            )

            is Message.ShowError -> copy(
                error = msg.error,
            )

            is Message.ToggleAddDateTime -> copy(
                isAddDateTimeToFileName = !isAddDateTimeToFileName,
            )

            is Message.StartExport -> copy(
                currentTask = State.Task.Exporting,
                error = null,
            )

            is Message.StartFilePathChoosing -> copy(
                currentTask = State.Task.FilePathChoosing,
            )

            is Message.FinishExport, is Message.FinishFilePathChoosing -> copy(
                currentTask = State.Task.FormFilling,
            )

            is Message.ExitScreen -> copy(
                currentTask = State.Task.ExitingScreen,
            )
        }
}