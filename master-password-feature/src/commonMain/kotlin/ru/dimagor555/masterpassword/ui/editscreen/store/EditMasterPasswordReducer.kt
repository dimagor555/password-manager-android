package ru.dimagor555.masterpassword.ui.editscreen.store

import ru.dimagor555.masterpassword.ui.editscreen.store.EditMasterPasswordStore.Message
import ru.dimagor555.masterpassword.ui.editscreen.store.EditMasterPasswordStore.State
import ru.dimagor555.mvicompose.abstraction.Reducer
import ru.dimagor555.ui.core.model.FieldState
import ru.dimagor555.ui.core.model.toggleVisibility

internal class EditMasterPasswordReducer : Reducer<State, Message> {

    override fun State.reduce(msg: Message) =
        when (msg) {
            is Message.ShowPassword -> updateCurrPassword(
                currPassword.copy(text = msg.password),
            )

            is Message.ShowError -> updateCurrPassword(
                currPassword.copy(error = msg.error),
            )

            is Message.TogglePasswordVisibility -> updateCurrPassword(
                currPassword.toggleVisibility(),
            )

            is Message.GoToPrimaryStage -> goToPrimaryStage()

            is Message.GoToConfirmStage -> copy(
                stage = State.Stage.Confirm,
            )

            is Message.StartSaving -> copy(
                currentTask = State.Task.Saving,
            )

            is Message.StartSettingUpBiometric -> copy(
                currentTask = State.Task.SettingUpBiometric(),
            )

            is Message.ShowBiometricSetUpDialog -> copy(
                currentTask = State.Task.SettingUpBiometric(
                    isBiometricSetUpDialogVisible = true,
                )
            )

            is Message.HideBiometricSetUpDialog -> copy(
                currentTask = State.Task.SettingUpBiometric(
                    isBiometricSetUpDialogVisible = false,
                )
            )

            is Message.ExitScreen -> copy(
                currentTask = State.Task.ExitingScreen(msg.isSuccess),
            )
        }

    private fun State.updateCurrPassword(password: FieldState.Password): State =
        updatePasswordOnStage(password, stage)

    private fun State.updatePasswordOnStage(
        password: FieldState.Password,
        stage: State.Stage,
    ): State =
        copy(
            passwordsByStages = passwordsByStages
                .toMutableMap()
                .apply { this[stage] = password },
        )

    private fun State.goToPrimaryStage(): State =
        copy(stage = State.Stage.Primary)
            .updatePasswordOnStage(FieldState.Password(), State.Stage.Confirm)
}