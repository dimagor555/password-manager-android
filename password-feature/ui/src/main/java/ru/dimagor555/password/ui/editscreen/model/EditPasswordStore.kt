package ru.dimagor555.password.ui.editscreen.model

import ru.dimagor555.mvicompose.abstraction.Store
import ru.dimagor555.mvicompose.implementation.StoreImpl
import ru.dimagor555.password.ui.editscreen.model.EditPasswordStore.*

internal class EditPasswordStore(
    useCases: EditPasswordUseCases
) : Store<Action, State, SideEffect> by StoreImpl(
    initialState = State(),
    actor = EditPasswordActor(useCases),
    reducer = EditPasswordReducer()
) {

    data class State(
        val passwordId: Int = 0,
        val initialPassword: PasswordDto? = null,
        val isSavingStarted: Boolean = false,
        val exitScreenState: ExitScreenState = ExitScreenState.NotExit
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
        data class LoadPassword(val passwordId: Int) : Action

        data class OnPasswordValidationSucceed(val password: PasswordDto) : Action
        object OnPasswordValidationFailed : Action

        object RequestExitScreen : Action, Message
        object DismissExitScreenRequest : Action, Message

        object ForceExitScreen : Action
    }

    sealed interface Message {
        data class SetInitialPassword(val passwordId: Int, val password: PasswordDto) : Message

        object ShowSaveDialog : Message

        object StartSaving : Message

        object ExitScreen : Message
    }

    sealed interface SideEffect {
        data class PasswordLoaded(val password: PasswordDto) : SideEffect

        object RequestValidatePassword : SideEffect
    }
}