package ru.dimagor555.masterpassword.ui.loginscreen.model

import kotlinx.coroutines.delay
import me.aartikov.sesame.localizedstring.LocalizedString
import ru.dimagor555.masterpassword.ui.loginscreen.model.LoginStore.*
import ru.dimagor555.masterpassword.R
import ru.dimagor555.mvicompose.abstraction.Actor

internal class LoginActor(
    private val useCases: LoginUseCases
) : Actor<State, Action, Message, Nothing>() {

    override suspend fun onAction(action: Action) {
        when (action) {
            is Action.ChangePassword -> changePassword(action.password)
            Action.TogglePasswordVisibility -> sendMessage(Message.TogglePasswordVisibility)
            Action.LoginByPassword -> startLogin()
            Action.EnableBiometricLogin -> sendMessage(Message.EnableBiometricLogin)
            Action.ExitScreenWithSuccess -> sendMessage(Message.ExitScreenWithSuccess)
        }
    }

    private suspend fun changePassword(password: String) {
        sendMessage(Message.ShowPassword(password))
        sendMessage(Message.ShowError(null))
    }

    private suspend fun startLogin() {
        sendMessage(Message.ShowError(null))
        sendMessage(Message.DisableLogin)
        loginByPassword(getState())
    }

    private suspend fun loginByPassword(state: State) {
        val isLoginSuccessful = useCases.loginByPassword(password = state.password.text)
        handleLoginResult(isLoginSuccessful)
    }

    private suspend fun handleLoginResult(isLoginSuccessful: Boolean) {
        if (isLoginSuccessful)
            sendMessage(Message.ExitScreenWithSuccess)
        else
            onLoginFailed()
    }

    private suspend fun onLoginFailed() {
        delay(LOGIN_FAILED_TIMEOUT)
        showLoginFailError()
        sendMessage(Message.EnableLogin)
    }

    private suspend fun showLoginFailError() {
        val error = LocalizedString.resource(R.string.incorrect_password_error)
        sendMessage(Message.ShowError(error))
    }

    companion object {
        private const val LOGIN_FAILED_TIMEOUT = 1000L
    }
}