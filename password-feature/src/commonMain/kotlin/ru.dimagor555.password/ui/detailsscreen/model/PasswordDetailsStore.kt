package ru.dimagor555.password.ui.detailsscreen.model

import dev.icerock.moko.resources.desc.StringDesc
import ru.dimagor555.mvicompose.abstraction.Store
import ru.dimagor555.mvicompose.implementation.StoreImpl
import ru.dimagor555.password.domain.folder.Folder
import ru.dimagor555.password.ui.detailsscreen.model.PasswordDetailsStore.*

internal class PasswordDetailsStore : Store<Action, State, SideEffect> by StoreImpl(
    initialState = State(),
    actor = PasswordDetailsActor(),
    reducer = PasswordDetailsReducer(),
) {

    data class State(
        val passwordId: String = "",
        val parentId: String = Folder.ROOT_FOLDER_ID,
        val passwordState: PasswordState = PasswordState(),
        val isLoading: Boolean = true,
        val isRemoveDialogVisible: Boolean = false,
        val isExitScreen: Boolean = false,
    )

    sealed interface Action {
        data class LoadPassword(val passwordId: String, val parentId: String) : Action

        object TogglePasswordVisibility : Action

        object ToggleFavourite : Action

        object CopyPassword : Action
        object CopyLogin : Action

        data class ChangeRemoveDialogVisibility(val visible: Boolean) : Action

        object RemovePassword : Action
    }

    sealed interface Message {
        data class ChangePasswordId(val passwordId: String) : Message

        data class ChangeParentId(val parentId: String) : Message

        data class ShowPassword(val passwordState: PasswordState) : Message

        object FinishLoading : Message

        data class ChangeRemoveDialogVisibility(val visible: Boolean) : Message

        object ExitScreen : Message
    }

    sealed interface SideEffect {
        data class ShowMessage(val message: StringDesc) : SideEffect
    }
}