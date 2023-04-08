package ru.dimagor555.masterpassword.ui.editscreen.store

import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.dimagor555.masterpassword.ui.editscreen.store.EditMasterPasswordStore.*
import ru.dimagor555.masterpassword.usecase.password.ValidatePasswordUseCase
import ru.dimagor555.mvicompose.abstraction.Actor
import ru.dimagor555.password.validation.ui.desc
import ru.dimagor555.res.core.MR

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
                is Action.TogglePasswordVisibility -> togglePasswordVisibility()
                is Action.GoNext -> goNext()
                is Action.GoBack -> goBack()
                is Action.ShowBiometricSetUpDialog -> showBiometricSetUpDialog()
                is Action.HideBiometricSetUpDialog -> hideBiometricSetUpDialog()
                is Action.OnFinishSettingUpBiometric -> onFinishSettingUpBiometric()
            }
        }

        protected abstract suspend fun changePassword(password: String)

        private suspend fun togglePasswordVisibility() {
            if (getState().isFormFillingInProgress) {
                sendMessage(Message.TogglePasswordVisibility)
            }
        }

        protected abstract suspend fun goNext()

        protected abstract suspend fun goBack()

        private suspend fun showBiometricSetUpDialog() {
            sendMessageIfSettingUpBiometricInProgress(Message.ShowBiometricSetUpDialog)
        }

        private suspend fun sendMessageIfSettingUpBiometricInProgress(message: Message) {
            if (getState().isSettingUpBiometricInProgress) {
                sendMessage(message)
            }
        }

        private suspend fun hideBiometricSetUpDialog() {
            sendMessageIfSettingUpBiometricInProgress(Message.HideBiometricSetUpDialog)
        }

        private suspend fun onFinishSettingUpBiometric() {
            sendMessageIfSettingUpBiometricInProgress(
                Message.ExitScreen(isSuccess = true),
            )
        }
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
            if (isPasswordValid(password)) {
                sendMessage(Message.GoToConfirmStage)
            } else {
                showValidationError(password)
            }
        }

        private fun isPasswordValid(password: String): Boolean {
            val validationResult = useCases.validatePassword(password)
            return validationResult.hasNoErrors()
        }

        private fun ValidatePasswordUseCase.Result.hasNoErrors() =
            (textValidationErrors + passwordValidationErrors).isEmpty()

        override suspend fun goBack() {
            sendMessage(Message.ExitScreen(isSuccess = false))
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
            if (isValid) {
                finishEditing(state.primaryPassword.text)
            } else {
                showValidationError()
            }
        }

        private fun isPasswordValid(confirmPassword: String) =
            getState().primaryPassword.text == confirmPassword

        private suspend fun finishEditing(primaryPassword: String) {
            if (getState().isFormFillingInProgress.not()) {
                return
            }
            sendMessage(Message.StartSaving)
            useCases.setMasterPassword(primaryPassword)
            onSavingFinished()
        }

        private suspend fun onSavingFinished() {
            if (useCases.isBiometricAvailable()) {
                sendMessage(Message.StartSettingUpBiometric)
            } else {
                sendMessage(Message.ExitScreen(isSuccess = true))
            }
        }

        private suspend fun showValidationError() {
            val error = MR.strings.error_password_not_match.desc()
            sendMessage(Message.ShowError(error))
        }

        override suspend fun goBack() {
            when (getState().currentTask) {
                is State.Task.FormFilling -> sendMessage(Message.GoToPrimaryStage)
                is State.Task.SettingUpBiometric -> onAction(Action.OnFinishSettingUpBiometric)
                else -> Unit
            }
        }
    }
}