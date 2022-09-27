package ru.dimagor555.password.ui.detailsscreen.model

import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.desc
import ru.dimagor555.mvicompose.abstraction.Actor
import ru.dimagor555.password.domain.Password
import ru.dimagor555.password.ui.detailsscreen.model.PasswordDetailsStore.*
import ru.dimagor555.res.core.MR
import ru.dimagor555.ui.core.util.stringResource

internal class PasswordDetailsActor(
    private val useCases: PasswordDetailsUseCases
) : Actor<State, Action, Message, SideEffect>() {

    override suspend fun onAction(action: Action) {
        when (action) {
            is Action.LoadPassword -> loadPassword(action.passwordId)
            Action.TogglePasswordVisibility -> togglePasswordVisibility()
            Action.ToggleFavourite -> toggleFavourite(getState().passwordId)
            Action.CopyPassword -> copyPassword(getState().passwordId)
            Action.CopyLogin -> copyLogin(getState().passwordId)
            is Action.ChangeRemoveDialogVisibility -> sendMessage(
                Message.ChangeRemoveDialogVisibility(action.visible)
            )
            Action.RemovePassword -> removePassword(getState().passwordId)
        }
    }

    private suspend fun loadPassword(passwordId: Int) {
        sendMessage(Message.ChangePasswordId(passwordId))
        observePassword()
    }

    private suspend fun observePassword() {
        useCases.observePassword(getState().passwordId)
            .collect {
                if (it != null)
                    onNewPassword(it)
                else
                    sendMessage(Message.ExitScreen)
            }
    }

    private suspend fun onNewPassword(password: Password) {
        val passwordState = password.toPasswordState()
        sendMessage(Message.ShowPasswordState(passwordState))
        updatePasswordTextIfVisible()
        sendMessage(Message.FinishLoading)
    }

    private suspend fun updatePasswordTextIfVisible() {
        val isVisible = getState().passwordText.isVisible
        if (!isVisible)
            return
        val newPasswordText = createVisiblePasswordText(decryptPassword())
        sendMessage(Message.ShowPasswordText(newPasswordText))
    }

    private suspend fun togglePasswordVisibility() {
        val isVisible = getState().passwordText.isVisible
        val newPasswordText = createToggledPasswordText(isVisible)
        sendMessage(Message.ShowPasswordText(newPasswordText))
    }

    private suspend fun createToggledPasswordText(isVisible: Boolean) =
        when (isVisible) {
            true -> createHiddenPasswordText()
            false -> createVisiblePasswordText(decryptPassword())
        }

    private suspend fun decryptPassword() = useCases.decryptPassword(getState().passwordId)

    private suspend fun toggleFavourite(passwordId: Int) {
        useCases.toggleFavourite(passwordId)
    }

    private suspend fun copyPassword(passwordId: Int) {
        useCases.copyPassword(passwordId)
        showMessage(MR.strings.password_copied)
    }

    private suspend fun showMessage(resource: StringResource) {
        val message = resource.desc()
        sendSideEffect(SideEffect.ShowMessage(message))
    }

    private suspend fun copyLogin(passwordId: Int) {
        useCases.copyLogin(passwordId)
        showMessage(MR.strings.login_copied)
    }

    private suspend fun removePassword(passwordId: Int) {
        useCases.removePassword(passwordId)
    }
}