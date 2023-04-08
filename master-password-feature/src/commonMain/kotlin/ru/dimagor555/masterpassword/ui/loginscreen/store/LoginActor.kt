package ru.dimagor555.masterpassword.ui.loginscreen.store

import dev.icerock.moko.resources.desc.desc
import kotlinx.coroutines.delay
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.dimagor555.masterpassword.ui.loginscreen.store.LoginStore.*
import ru.dimagor555.mvicompose.abstraction.Actor
import ru.dimagor555.res.core.MR
import ru.dimagor555.masterpassword.usecase.password.LoginByPasswordUseCase.Result as LoginResult

internal class LoginActor : Actor<State, Action, Message, Nothing>(), KoinComponent {

    private val useCases: LoginUseCases by inject()

    override suspend fun onAction(action: Action) {
        when (action) {
            is Action.ChangePassword -> changePassword(action.password)
            is Action.TogglePasswordVisibility -> sendMessage(Message.TogglePasswordVisibility)
            is Action.LoginByPassword -> startLogin()
            is Action.ExitScreenWithSuccess -> sendMessage(Message.ExitScreenWithSuccess)
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
        val loginResult = useCases.loginByPassword(password = state.password.text)
        handleLoginResult(loginResult)
    }

    private suspend fun handleLoginResult(loginResult: LoginResult) =
        when (loginResult) {
            is LoginResult.Success -> sendMessage(Message.ExitScreenWithSuccess)
            is LoginResult.InvalidPassword -> onLoginFailed()
        }

    private suspend fun onLoginFailed() {
        delay(LOGIN_FAILED_TIMEOUT)
        showLoginFailError()
        sendMessage(Message.EnableLogin)
    }

    private suspend fun showLoginFailError() {
        val error = MR.strings.incorrect_password_error.desc()
        sendMessage(Message.ShowError(error))
    }

    companion object {
        private const val LOGIN_FAILED_TIMEOUT = 1000L
    }
}