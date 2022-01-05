package ru.dimagor555.password.editscreen.model

import ru.dimagor555.mvicompose.abstraction.Reducer
import ru.dimagor555.password.editscreen.model.EditPasswordStore.*

internal class EditPasswordReducer : Reducer<State, Message> {
    override fun State.reduce(msg: Message) = when (msg) {
        is Message.SetInitialPassword -> copy(
            passwordId = msg.passwordId,
            initialPassword = msg.password
        )
        Action.RequestExitScreen -> copy(exitScreenState = State.ExitScreenState.Request())
        Action.DismissExitScreenRequest -> copy(exitScreenState = State.ExitScreenState.NotExit)
        Message.ShowSaveDialog -> copy(
            exitScreenState = State.ExitScreenState.Request(isSaveDialogVisible = true)
        )
        Message.StartSaving -> copy(isSavingStarted = true)
        Message.ExitScreen -> copy(exitScreenState = State.ExitScreenState.Exit)
    }
}