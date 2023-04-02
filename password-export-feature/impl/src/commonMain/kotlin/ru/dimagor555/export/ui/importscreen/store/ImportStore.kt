package ru.dimagor555.export.ui.importscreen.store

import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import ru.dimagor555.export.ui.importscreen.store.ImportStore.*
import ru.dimagor555.mvicompose.abstraction.Store
import ru.dimagor555.mvicompose.implementation.StoreImpl
import ru.dimagor555.res.core.MR

internal class ImportStore : Store<Action, State, SideEffect> by StoreImpl(
    initialState = State(),
    actor = ImportActor(),
    reducer = ImportReducer(),
) {

    data class State(
        val fileState: FileState = FileState.NotSelected,
        val isClearBeforeImport: Boolean = true,
        val isMakeBackup: Boolean = true,
        val currentTask: Task = Task.FormFilling,
        val error: StringDesc? = null,
    ) {

        val selectedFileText: StringDesc
            get() = when (fileState) {
                is FileState.NotSelected -> MR.strings.nothing_selected.desc()
                is FileState.Selected -> fileState.pathOrName.desc()
            }

        val isFileNotSelected: Boolean
            get() = fileState is FileState.NotSelected

        val selectedFileUri: String
            get() = (fileState as FileState.Selected).uri

        val isFileChoosingEnabled: Boolean
            get() = currentTask is Task.FormFilling

        val isImportEnabled: Boolean
            get() = currentTask is Task.FormFilling

        val isImportInProgress: Boolean
            get() = currentTask is Task.Importing

        val isExitScreen: Boolean
            get() = currentTask is Task.ExitingScreen

        sealed interface FileState {

            object NotSelected : FileState

            data class Selected(
                val uri: String,
                val pathOrName: String,
            ) : FileState
        }

        sealed interface Task {
            object FormFilling : Task
            object FileChoosing : Task
            object Importing : Task
            object ExitingScreen : Task
        }
    }

    sealed interface Action {

        object TryChooseFile : Action
        data class OnChooseFileSuccess(val fileUri: String) : Action
        object OnChooseFileFailure : Action

        object ToggleClearBeforeImport : Action
        object ToggleMakeBackup : Action

        object TryImport : Action
    }

    sealed interface Message {

        data class ShowSelectedFile(
            val uri: String,
            val pathOrName: String,
        ) : Message

        data class ShowError(val error: StringDesc?) : Message

        object ToggleClearBeforeImport : Message
        object ToggleMakeBackup : Message

        object StartFileChoosing : Message
        object FinishFileChoosing : Message

        object StartImport : Message
        object FinishImport : Message

        object ExitScreen : Message
    }

    sealed interface SideEffect {

        object OpenFileChooser : SideEffect

        data class ShowMessage(val message: StringDesc) : SideEffect
    }
}