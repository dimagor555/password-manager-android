package ru.dimagor555.password.ui.editscreen.model

import ru.dimagor555.mvicompose.abstraction.Actor
import ru.dimagor555.password.ui.editscreen.model.EditPasswordStore.*
import ru.dimagor555.password.usecase.UpdatePasswordUseCase

internal class EditPasswordActor(
    private val useCases: EditPasswordUseCases
) : Actor<State, Action, Message, SideEffect>() {

    override suspend fun onAction(action: Action) = when (action) {
        is Action.LoadPassword -> loadPasswordIfNotLoaded(action.passwordId)
        is Action.OnPasswordValidationSucceed -> onPasswordValidationSucceed(action.password)
        Action.OnPasswordValidationFailed -> onPasswordValidationFailed()
        is Action.RequestExitScreen -> requestExitScreen()
        is Action.DismissExitScreenRequest -> sendMessage(action)
        Action.ForceExitScreen -> sendMessage(Message.ExitScreen)
    }

    private suspend fun loadPasswordIfNotLoaded(passwordId: Int) {
        if (getState().initialPassword == null)
            loadPassword(passwordId)
    }

    private suspend fun loadPassword(passwordId: Int) {
        val passwordDto = loadPasswordDto(passwordId)
        sendMessage(Message.SetInitialPassword(passwordId, passwordDto))
        sendSideEffect(SideEffect.PasswordLoaded(passwordDto))
    }

    private suspend fun loadPasswordDto(passwordId: Int): PasswordDto {
        val password = useCases.getPassword(passwordId)
        val decryptedPasswordText = useCases.decryptPassword(passwordId)
        return PasswordDto(password.title, password.login, decryptedPasswordText)
    }

    private suspend fun onPasswordValidationSucceed(password: PasswordDto) {
        if (!getState().isExitScreenRequested)
            savePassword(password)
        else
            handleValidatedPasswordOnExitScreenRequest(password)
    }

    private suspend fun savePassword(passwordDto: PasswordDto) {
        if (getState().isSavingStarted)
            return
        sendMessage(Message.StartSaving)
        updatePassword(passwordDto)
        sendMessage(Message.ExitScreen)
    }

    private suspend fun updatePassword(passwordDto: PasswordDto) {
        useCases.updatePassword(
            UpdatePasswordUseCase.Params(
                getState().passwordId,
                passwordDto.title,
                passwordDto.login,
                passwordDto.password
            )
        )
    }

    private suspend fun handleValidatedPasswordOnExitScreenRequest(password: PasswordDto) {
        if (getState().initialPassword == password)
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
