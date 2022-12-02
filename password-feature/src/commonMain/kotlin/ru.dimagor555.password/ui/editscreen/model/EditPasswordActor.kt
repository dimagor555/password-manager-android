package ru.dimagor555.password.ui.editscreen.model

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.dimagor555.mvicompose.abstraction.Actor
import ru.dimagor555.password.domain.password.Password
import ru.dimagor555.password.domain.password.PasswordFields
import ru.dimagor555.password.domain.password.field.EncryptedPasswordField
import ru.dimagor555.password.domain.password.field.PASSWORD_FIELD_KEY
import ru.dimagor555.password.domain.password.updateFieldByKey
import ru.dimagor555.password.ui.editscreen.model.EditPasswordStore.*
import ru.dimagor555.password.usecase.password.UpdatePasswordUseCase

internal class EditPasswordActor : Actor<State, Action, Message, SideEffect>(), KoinComponent {

    private val useCases: EditPasswordUseCases by inject()

    override suspend fun onAction(action: Action) = when (action) {
        is Action.LoadPassword -> loadPasswordIfNotLoaded(action.passwordId)
        is Action.OnPasswordValidationSucceed -> onPasswordValidationSucceed(action.password)
        Action.OnPasswordValidationFailed -> onPasswordValidationFailed()
        is Action.RequestExitScreen -> requestExitScreen()
        is Action.DismissExitScreenRequest -> sendMessage(action)
        Action.ForceExitScreen -> sendMessage(Message.ExitScreen)
    }

    private suspend fun loadPasswordIfNotLoaded(passwordId: String) {
        if (getState().initialPassword == null)
            loadPassword(passwordId)
    }

    private suspend fun loadPassword(passwordId: String) {
        val passwordFields = loadPasswordFields(passwordId)
        sendMessage(Message.SetInitialPassword(passwordId, passwordFields))
        sendSideEffect(SideEffect.PasswordLoaded(passwordFields))
    }

    private suspend fun loadPasswordFields(passwordId: String): PasswordFields {
        val password = useCases.getPassword(passwordId)
        val decryptedPasswordText = useCases.decryptPassword(passwordId)
        return updateDecryptPassword(password, decryptedPasswordText)
    }

    private fun updateDecryptPassword(
        password: Password,
        decryptedPasswordText: String,
    ): PasswordFields =
        password.fields.updateFieldByKey<EncryptedPasswordField>(
            key = PASSWORD_FIELD_KEY,
        ) {
            EncryptedPasswordField(PASSWORD_FIELD_KEY, decryptedPasswordText)
        }

    private suspend fun onPasswordValidationSucceed(passwordFields: PasswordFields) {
        if (!getState().isExitScreenRequested)
            savePassword(passwordFields)
        else
            handleValidatedPasswordOnExitScreenRequest(passwordFields)
    }

    private suspend fun savePassword(passwordFields: PasswordFields) {
        if (getState().isSavingStarted)
            return
        sendMessage(Message.StartSaving)
        updatePassword(passwordFields)
    }

    private suspend fun updatePassword(passwordFields: PasswordFields) {
        val updateResult = useCases.updatePassword(
            UpdatePasswordUseCase.Params(
                getState().passwordId,
                passwordFields
            )
        )
        when (updateResult) {
            UpdatePasswordUseCase.Result.Success ->  sendMessage(Message.ExitScreen)
            UpdatePasswordUseCase.Result.Error -> sendSideEffect(SideEffect.ShowUnknownUpdateError)
            is UpdatePasswordUseCase.Result.ValidationError ->
                sendSideEffect(SideEffect.RequestShowUpdateErrors(updateResult.errorsByFieldTypes))
        }
    }

    private suspend fun handleValidatedPasswordOnExitScreenRequest(passwordFields: PasswordFields) {
        if (getState().initialPassword == passwordFields)
            sendMessage(Message.ExitScreen)
        else
            sendMessage(Message.ShowSaveDialog)
    }

    private suspend fun onPasswordValidationFailed() {
        if (getState().isExitScreenRequested)
            sendMessage(Message.ExitScreen)
    }

    private suspend fun requestExitScreen() {
        sendMessage(Action.RequestExitScreen)
        sendSideEffect(SideEffect.RequestValidatePassword)
    }
}
