package ru.dimagor555.masterpassword.editscreen.model

import me.aartikov.sesame.localizedstring.LocalizedString
import ru.dimagor555.masterpassword.editscreen.model.EditMasterPasswordStore.Action
import ru.dimagor555.masterpassword.editscreen.model.EditMasterPasswordStore.State
import ru.dimagor555.mvicompose.abstraction.Store
import ru.dimagor555.mvicompose.implementation.StoreImpl
import ru.dimagor555.ui.core.model.FieldState
import ru.dimagor555.ui.core.model.isError

internal class EditMasterPasswordStore(useCases: EditMasterPasswordUseCases) :
    Store<Action, State, Nothing> by StoreImpl(
        initialState = State(),
        actor = EditMasterPasswordActor(useCases),
        reducer = EditMasterPasswordReducer()
    ) {

    data class State(
        val stage: Stage = Stage.Primary,
        val passwordsByStages: Map<Stage, FieldState.Password> = initialPasswordsByStages,
        val isSavingStarted: Boolean = false,
        val exitScreenState: ExitScreenState = ExitScreenState.NotExit
    ) {
        val currPassword
            get() = passwordsByStages[stage]!!

        val isError
            get() = currPassword.isError

        val primaryPassword
            get() = passwordsByStages[Stage.Primary]!!

        enum class Stage {
            Primary, Confirm
        }

        sealed class ExitScreenState {
            object NotExit : ExitScreenState()
            data class Exit(val success: Boolean) : ExitScreenState()
        }

        companion object {
            private val initialPasswordsByStages = mapOf(
                *Stage.values()
                    .map { stage -> stage to FieldState.Password() }
                    .toTypedArray()
            )
        }
    }

    sealed class Action {
        data class ChangePassword(val password: String) : Action()
        object TogglePasswordVisibility : Action()

        object GoNext : Action()
        object GoBack : Action()
    }

    sealed class Message {
        data class ShowPassword(val password: String) : Message()
        data class ShowError(val error: LocalizedString?) : Message()
        object TogglePasswordVisibility : Message()

        object GoToPrimaryStage : Message()
        object GoToConfirmStage : Message()

        object ShowSavingStarted : Message()

        data class ExitScreen(val success: Boolean) : Message()
    }
}