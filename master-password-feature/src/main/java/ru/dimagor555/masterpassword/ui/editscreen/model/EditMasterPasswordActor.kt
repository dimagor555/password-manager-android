package ru.dimagor555.masterpassword.ui.editscreen.model

import me.aartikov.sesame.localizedstring.LocalizedString
import ru.dimagor555.masterpassword.ui.editscreen.model.EditMasterPasswordStore.*
import ru.dimagor555.masterpassword.R
import ru.dimagor555.masterpassword.usecase.ValidatePasswordUseCase
import ru.dimagor555.mvicompose.abstraction.Actor
import ru.dimagor555.password.validation.ui.toLocalizedString

internal class EditMasterPasswordActor(
    private val useCases: EditMasterPasswordUseCases
) : Actor<State, Action, Message, Nothing>() {

    override suspend fun onAction(action: Action) {
        when (getState().stage) {
            State.Stage.Primary -> PrimaryStageActionHandler().onAction(action)
            State.Stage.Confirm -> ConfirmStageActionHandler().onAction(action)
        }
    }

    private abstract inner class BaseActionHandler {
        suspend fun onAction(action: Action) {
            when (action) {
                is Action.ChangePassword -> changePassword(action.password)
                Action.TogglePasswordVisibility -> sendMessage(Message.TogglePasswordVisibility)
                Action.GoNext -> goNext()
                Action.GoBack -> goBack()
            }
        }

        protected abstract suspend fun changePassword(password: String)

        protected abstract suspend fun goNext()

        protected abstract suspend fun goBack()
    }

    private inner class PrimaryStageActionHandler : BaseActionHandler() {
        override suspend fun changePassword(password: String) {
            sendMessage(Message.ShowPassword(password))
            showValidationError(password)
        }

        private suspend fun showValidationError(password: String) {
            val validationResult = useCases.validatePassword(password)
            val error = validationResult.selectMostImportantError()
            sendMessage(Message.ShowError(error))
        }

        private fun ValidatePasswordUseCase.Result.selectMostImportantError(): LocalizedString? {
            val textError = textValidationErrors.firstOrNull()?.toLocalizedString()
            val passwordError = passwordValidationErrors.firstOrNull()?.toLocalizedString()
            return textError ?: passwordError
        }

        override suspend fun goNext() {
            val password = getState().currPassword.text
            if (isPasswordValid(password))
                sendMessage(Message.GoToConfirmStage)
            else
                showValidationError(password)
        }

        private suspend fun isPasswordValid(password: String): Boolean {
            val validationResult = useCases.validatePassword(password)
            return validationResult.hasNoErrors()
        }

        private fun ValidatePasswordUseCase.Result.hasNoErrors() =
            (textValidationErrors + passwordValidationErrors).isEmpty()

        override suspend fun goBack() {
            sendMessage(Message.ExitScreen(success = false))
        }
    }

    private inner class ConfirmStageActionHandler : BaseActionHandler() {
        override suspend fun changePassword(password: String) {
            sendMessage(Message.ShowPassword(password))
            sendMessage(Message.ShowError(null))
        }

        override suspend fun goNext() {
            val state = getState()
            val isValid = isPasswordValid(state.currPassword.text)
            if (isValid)
                finishEditing(state.primaryPassword.text)
            else
                showValidationError()
        }

        private fun isPasswordValid(confirmPassword: String) =
            getState().primaryPassword.text == confirmPassword

        private suspend fun finishEditing(primaryPassword: String) {
            if (getState().isSavingStarted)
                return
            sendMessage(Message.ShowSavingStarted)
            useCases.setMasterPassword(primaryPassword)
            sendMessage(Message.ExitScreen(success = true))
        }

        private suspend fun showValidationError() {
            val error = LocalizedString.resource(R.string.error_password_not_match)
            sendMessage(Message.ShowError(error))
        }

        override suspend fun goBack() {
            sendMessage(Message.GoToPrimaryStage)
        }
    }
}