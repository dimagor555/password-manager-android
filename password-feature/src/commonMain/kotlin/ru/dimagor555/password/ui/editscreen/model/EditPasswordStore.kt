package ru.dimagor555.password.ui.editscreen.model

import ru.dimagor555.mvicompose.abstraction.Store
import ru.dimagor555.mvicompose.implementation.StoreImpl
import ru.dimagor555.password.domain.folder.Folder
import ru.dimagor555.password.domain.password.PasswordFields
import ru.dimagor555.password.ui.editscreen.model.EditPasswordStore.*
import ru.dimagor555.password.validation.core.TextValidationError

internal class EditPasswordStore : Store<Action, State, SideEffect> by StoreImpl(
    initialState = State(),
    actor = EditPasswordActor(),
    reducer = EditPasswordReducer(),
) {

    data class State(
        val passwordId: String = "",
        val parentId: String = Folder.ROOT_FOLDER_ID,
        val initialPassword: PasswordFields? = null,
        val isSavingStarted: Boolean = false,
        val exitScreenState: ExitScreenState = ExitScreenState.NotExit,
    ) {
        val isSaveDialogVisible
            get() = exitScreenState is ExitScreenState.Request
                    && exitScreenState.isSaveDialogVisible

        val isExitScreenRequested
            get() = exitScreenState is ExitScreenState.Request

        val isExitScreen
            get() = exitScreenState is ExitScreenState.Exit

        sealed interface ExitScreenState {
            object NotExit : ExitScreenState
            data class Request(val isSaveDialogVisible: Boolean = false) : ExitScreenState
            object Exit : ExitScreenState
        }
    }

    sealed interface Action {
        data class LoadPassword(val passwordId: String) : Action

        data class OnPasswordValidationSucceed(val passwordFields: PasswordFields) : Action
        object OnPasswordValidationFailed : Action

        object RequestExitScreen : Action, Message
        object DismissExitScreenRequest : Action, Message

        object ForceExitScreen : Action
    }

    sealed interface Message {
        data class SetInitialPassword(
            val passwordId: String,
            val passwordFields: PasswordFields,
        ) : Message

        object ShowSaveDialog : Message

        object StartSaving : Message

        object ExitScreen : Message
    }

    sealed interface SideEffect {
        data class PasswordLoaded(val fields: PasswordFields) : SideEffect

        data class RequestShowUpdateErrors(
            val errorsByFieldTypes: Map<String, TextValidationError?>
        ) : SideEffect

        object RequestValidatePassword : SideEffect

        object ShowUnknownUpdateError : SideEffect
    }
}