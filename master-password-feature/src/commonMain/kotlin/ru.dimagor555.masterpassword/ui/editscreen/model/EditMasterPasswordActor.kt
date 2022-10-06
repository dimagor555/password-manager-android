package ru.dimagor555.masterpassword.ui.editscreen.model

import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.dimagor555.masterpassword.ui.editscreen.model.EditMasterPasswordStore.*
import ru.dimagor555.res.core.MR
import ru.dimagor555.masterpassword.usecase.ValidatePasswordUseCase
import ru.dimagor555.mvicompose.abstraction.Actor
import ru.dimagor555.password.validation.ui.desc

internal class EditMasterPasswordActor : Actor<State, Action, Message, Nothing>(), KoinComponent {

    private val useCases: EditMasterPasswordUseCases by inject()

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

        private fun ValidatePasswordUseCase.Result.selectMostImportantError(): StringDesc? {
            val textError = textValidationErrors.firstOrNull()?.desc()
            val passwordError = passwordValidationErrors.firstOrNull()?.desc()
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
            val error = MR.strings.error_password_not_match.desc()
            sendMessage(Message.ShowError(error))
        }

        override suspend fun goBack() {
            sendMessage(Message.GoToPrimaryStage)
        }
    }
}