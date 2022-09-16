package ru.dimagor555.password.ui.detailsscreen.model

import me.aartikov.sesame.localizedstring.LocalizedString
import ru.dimagor555.mvicompose.abstraction.Store
import ru.dimagor555.mvicompose.implementation.StoreImpl
import ru.dimagor555.password.ui.detailsscreen.model.PasswordDetailsStore.*

internal class PasswordDetailsStore(
    useCases: PasswordDetailsUseCases
) : Store<Action, State, SideEffect> by StoreImpl(
    initialState = State(),
    actor = PasswordDetailsActor(useCases),
    reducer = PasswordDetailsReducer()
) {

    data class State(
        val passwordId: Int = 0,
        val passwordState: PasswordState = PasswordState(),
        val passwordText: PasswordTextState = PasswordTextState(),
        val isLoading: Boolean = true,
        val isRemoveDialogVisible: Boolean = false,
        val isExitScreen: Boolean = false
    )

    sealed interface Action {
        data class LoadPassword(val passwordId: Int) : Action

        object TogglePasswordVisibility : Action

        object ToggleFavourite : Action

        object CopyPassword : Action
        object CopyLogin : Action

        data class ChangeRemoveDialogVisibility(val visible: Boolean) : Action

        object RemovePassword : Action
    }

    sealed interface Message {
        data class ChangePasswordId(val passwordId: Int) : Message

        data class ShowPasswordState(val passwordState: PasswordState) : Message
        data class ShowPasswordText(val passwordText: PasswordTextState) : Message

        object FinishLoading : Message

        data class ChangeRemoveDialogVisibility(val visible: Boolean) : Message

        object ExitScreen : Message
    }

    sealed interface SideEffect {
        data class ShowMessage(val message: LocalizedString) : SideEffect
    }
}