package ru.dimagor555.password.ui.detailsscreen.model

import ru.dimagor555.mvicompose.abstraction.Reducer
import ru.dimagor555.password.ui.detailsscreen.model.PasswordDetailsStore.Message
import ru.dimagor555.password.ui.detailsscreen.model.PasswordDetailsStore.State

internal class PasswordDetailsReducer : Reducer<State, Message> {
    override fun State.reduce(msg: Message) =
        when (msg) {
            is Message.ChangePasswordId -> copy(passwordId = msg.passwordId)
            is Message.ShowPasswordState -> copy(passwordState = msg.passwordState)
            is Message.ShowPasswordText -> copy(passwordText = msg.passwordText)
            Message.FinishLoading -> copy(isLoading = false)
            is Message.ChangeRemoveDialogVisibility -> copy(isRemoveDialogVisible = msg.visible)
            Message.ExitScreen -> copy(isExitScreen = true)
        }
}