package ru.dimagor555.masterpassword.loginscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.aartikov.sesame.localizedstring.LocalizedString
import ru.dimagor555.masterpassword.loginscreen.model.LoginEvent
import ru.dimagor555.masterpassword.loginscreen.model.LoginViewState
import ru.dimagor555.masterpassword.ui.core.model.PasswordErrorIndicatorState
import ru.dimagor555.ui.core.model.toggleVisibility
import javax.inject.Inject

@HiltViewModel
internal class LoginViewModel @Inject constructor(
    private val useCases: LoginUseCases
) : ViewModel() {
    private val _state = MutableStateFlow(LoginViewState())
    val state = _state.asStateFlow()

    fun sendEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnPasswordChanged -> changePassword(event.password)
            LoginEvent.TogglePasswordVisibility -> togglePasswordVisibility()
            is LoginEvent.ShowError -> showError(event.error)
            LoginEvent.LoginByPassword -> loginByPassword()
            LoginEvent.LoginByBiometrics -> loginByBiometrics()
            LoginEvent.EnableBiometricLogin -> enableBiometricLoginButton()
            LoginEvent.ExitLoginScreenWithSuccess -> exitScreenWithSuccess()
        }
    }

    private fun changePassword(password: String) {
        _state.update {
            it.copy(
                passwordState = it.passwordState.copy(text = password)
            )
        }
    }

    private fun togglePasswordVisibility() {
        _state.update {
            it.copy(passwordState = it.passwordState.toggleVisibility())
        }
    }

    private fun showError(error: LocalizedString?) {
        _state.update {
            it.copy(
                passwordState = it.passwordState.copy(error = error),
                errorIndicatorState = chooseErrorIndicatorState(error)
            )
        }
    }

    private fun chooseErrorIndicatorState(error: LocalizedString?) = when (error) {
        null -> PasswordErrorIndicatorState.NoError
        else -> PasswordErrorIndicatorState.Error
    }

    private fun loginByPassword() {
        sendEvent(LoginEvent.ShowError(null))
        login { useCases.loginByPassword(_state.value.passwordState.text) }
    }

    private fun login(login: suspend () -> Boolean) = viewModelScope.launch {
        sendEvent(LoginEvent.ShowError(null))
        onLoginFinished(isLoginSuccessful = login())
    }

    private fun onLoginFinished(isLoginSuccessful: Boolean) {
        if (isLoginSuccessful)
            sendEvent(LoginEvent.ExitLoginScreenWithSuccess)
        else
            onLoginFailed()
    }

    private fun onLoginFailed() {
        val error = LocalizedString.resource(R.string.incorrect_password_error)
        sendEvent(LoginEvent.ShowError(error))
    }

    private fun loginByBiometrics() {
        sendEvent(LoginEvent.ExitLoginScreenWithSuccess)
    }

    private fun enableBiometricLoginButton() {
        _state.update { it.copy(isBiometricLoginButtonVisible = true) }
    }

    private fun exitScreenWithSuccess() {
        _state.update { it.copy(isExitScreenWithSuccess = true) }
    }
}
