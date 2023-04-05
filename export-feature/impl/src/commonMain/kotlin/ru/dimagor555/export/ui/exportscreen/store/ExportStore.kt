package ru.dimagor555.export.ui.exportscreen.store

import dev.icerock.moko.resources.desc.StringDesc
import ru.dimagor555.export.ui.exportscreen.store.ExportStore.*
import ru.dimagor555.mvicompose.abstraction.Store
import ru.dimagor555.mvicompose.implementation.StoreImpl
import ru.dimagor555.ui.core.model.FieldState

internal class ExportStore : Store<Action, State, SideEffect> by StoreImpl(
    initialState = State(),
    actor = ExportActor(),
    reducer = ExportReducer(),
) {

    data class State(
        val fileName: FieldState.Text = FieldState.Text(text = INITIAL_FILE_NAME),
        val isAddDateTimeToFileName: Boolean = true,
        val currentTask: Task = Task.FormFilling,
        val error: StringDesc? = null,
    ) {

        val isFormFillingInProgress: Boolean
            get() = currentTask is Task.FormFilling

        val isExportInProgress: Boolean
            get() = currentTask is Task.Exporting

        val isExitScreen: Boolean
            get() = currentTask is Task.ExitingScreen

        sealed interface Task {
            object FormFilling : Task
            object FilePathChoosing : Task
            object Exporting : Task
            object ExitingScreen : Task
        }

        companion object {
            private const val INITIAL_FILE_NAME = "export"
        }
    }

    sealed interface Action {

        data class ChangeFileName(val fileName: String) : Action
        object ToggleAddDateTime : Action

        object TryChooseFilePath : Action
        data class OnChooseFilePathSuccess(val fileUri: String) : Action
        object OnChooseFilePathFailure : Action
    }

    sealed interface Message {

        data class ShowFileName(val fileName: String) : Message
        data class ShowFileNameError(val error: StringDesc?) : Message

        data class ShowError(val error: StringDesc?) : Message

        object ToggleAddDateTime : Message

        object StartFilePathChoosing : Message
        object FinishFilePathChoosing : Message

        object StartExport : Message
        object FinishExport : Message

        object ExitScreen : Message
    }

    sealed interface SideEffect {

        data class OpenFilePathChooser(val fileName: String) : SideEffect

        data class ShowMessage(val message: StringDesc) : SideEffect
    }
}