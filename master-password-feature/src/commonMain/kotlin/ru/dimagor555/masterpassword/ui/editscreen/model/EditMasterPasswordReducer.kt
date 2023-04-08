package ru.dimagor555.masterpassword.ui.editscreen.model

import ru.dimagor555.masterpassword.ui.editscreen.model.EditMasterPasswordStore.Message
import ru.dimagor555.masterpassword.ui.editscreen.model.EditMasterPasswordStore.State
import ru.dimagor555.mvicompose.abstraction.Reducer
import ru.dimagor555.ui.core.model.FieldState
import ru.dimagor555.ui.core.model.toggleVisibility

internal class EditMasterPasswordReducer : Reducer<State, Message> {
    override fun State.reduce(msg: Message) =
        when (msg) {
            is Message.ShowPassword -> updateCurrPassword(currPassword.copy(text = msg.password))
            is Message.ShowError -> updateCurrPassword(currPassword.copy(error = msg.error))
            Message.TogglePasswordVisibility -> updateCurrPassword(currPassword.toggleVisibility())
            Message.GoToPrimaryStage -> goToPrimaryStage()
            Message.GoToConfirmStage -> copy(stage = State.Stage.Confirm)
            Message.ShowSavingStarted -> copy(isSavingStarted = true)
            is Message.ExitScreen -> copy(exitScreenState = State.ExitScreenState.Exit(msg.success))
        }

    private fun State.updateCurrPassword(password: FieldState.Password) =
        updatePasswordOnStage(password, stage)

    private fun State.updatePasswordOnStage(password: FieldState.Password, stage: State.Stage) =
        copy(
            passwordsByStages = passwordsByStages
                .toMutableMap()
                .apply { this[stage] = password }
        )

    private fun State.goToPrimaryStage() = copy(stage = State.Stage.Primary)
        .updatePasswordOnStage(FieldState.Password(), State.Stage.Confirm)
}