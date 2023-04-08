package ru.dimagor555.masterpassword.ui.editscreen.store

import dev.icerock.moko.resources.desc.StringDesc
import ru.dimagor555.masterpassword.ui.editscreen.store.EditMasterPasswordStore.Action
import ru.dimagor555.masterpassword.ui.editscreen.store.EditMasterPasswordStore.State
import ru.dimagor555.mvicompose.abstraction.Store
import ru.dimagor555.mvicompose.implementation.StoreImpl
import ru.dimagor555.ui.core.model.FieldState
import ru.dimagor555.ui.core.model.isError

internal class EditMasterPasswordStore : Store<Action, State, Nothing> by StoreImpl(
    initialState = State(),
    actor = EditMasterPasswordActor(),
    reducer = EditMasterPasswordReducer(),
) {

    data class State(
        val stage: Stage = Stage.Primary,
        val passwordsByStages: Map<Stage, FieldState.Password> = initialPasswordsByStages,
        val currentTask: Task = Task.FormFilling,
    ) {

        val currPassword: FieldState.Password
            get() = passwordsByStages[stage]!!

        val isError: Boolean
            get() = currPassword.isError

        val primaryPassword: FieldState.Password
            get() = passwordsByStages[Stage.Primary]!!

        val isFormFillingInProgress: Boolean
            get() = currentTask is Task.FormFilling

        val isSettingUpBiometricInProgress: Boolean
            get() = currentTask is Task.SettingUpBiometric

        val isBiometricSetUpDialogVisible: Boolean
            get() = (currentTask as? Task.SettingUpBiometric)
                ?.isBiometricSetUpDialogVisible
                ?: false

        enum class Stage {
            Primary, Confirm
        }

        sealed interface Task {

            object FormFilling : Task

            object Saving : Task

            data class SettingUpBiometric(
                val isBiometricSetUpDialogVisible: Boolean = false,
            ) : Task

            data class ExitingScreen(
                val isSuccess: Boolean,
            ) : Task
        }

        companion object {

            private val initialPasswordsByStages = mapOf(
                *Stage.values()
                    .map { stage -> stage to FieldState.Password() }
                    .toTypedArray()
            )
        }
    }

    sealed interface Action {

        data class ChangePassword(val password: String) : Action
        object TogglePasswordVisibility : Action

        object GoNext : Action
        object GoBack : Action

        object ShowBiometricSetUpDialog : Action
        object HideBiometricSetUpDialog : Action
        object OnFinishSettingUpBiometric : Action
    }

    sealed interface Message {

        data class ShowPassword(val password: String) : Message
        data class ShowError(val error: StringDesc?) : Message
        object TogglePasswordVisibility : Message

        object GoToPrimaryStage : Message
        object GoToConfirmStage : Message

        object StartSaving : Message

        object StartSettingUpBiometric : Message
        object ShowBiometricSetUpDialog : Message
        object HideBiometricSetUpDialog : Message

        data class ExitScreen(val isSuccess: Boolean) : Message
    }
}