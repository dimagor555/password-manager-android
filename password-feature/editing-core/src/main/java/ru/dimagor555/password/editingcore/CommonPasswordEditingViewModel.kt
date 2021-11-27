package ru.dimagor555.password.editingcore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.dimagor555.password.editingcore.model.*
import ru.dimagor555.password.usecase.ValidatePasswordUseCase

abstract class CommonPasswordEditingViewModel(
    private val useCases: CommonPasswordEditingUseCases
) : ViewModel() {
    private val _state = MutableStateFlow(PasswordEditingViewState())
    internal val state = _state.asStateFlow()

    init {
        initState()
    }

    private fun initState() {
        viewModelScope.launch(Dispatchers.Default) {
            _state.value = createInitialState()
        }
    }

    private suspend fun createInitialState() =
        getInitialPasswordDto()?.toPasswordEditingViewState()
            ?: PasswordEditingViewState()

    protected abstract suspend fun getInitialPasswordDto(): PasswordEditingDto?

    internal fun sendEvent(event: PasswordEditingEvent) {
        when (event) {
            is PasswordEditingEvent.OnTitleChanged -> changeTitle(event.title)
            is PasswordEditingEvent.OnLoginChanged -> changeLogin(event.login)
            is PasswordEditingEvent.OnPasswordChanged -> changePassword(event.password)
            PasswordEditingEvent.TogglePasswordVisibility -> togglePasswordVisibility()
            PasswordEditingEvent.TryFinishEditing -> tryFinishEditing()
            PasswordEditingEvent.ExitScreen -> exitScreen()
        }
    }

    private fun changeTitle(title: String) = viewModelScope.launch(Dispatchers.Default) {
        val error = useCases.validateTitle(title)
        val newTitleState = FieldViewState.Text(title, error?.toTextFieldError())
        _state.update { it.copy(titleState = newTitleState) }
    }

    private fun changeLogin(login: String) = viewModelScope.launch(Dispatchers.Default) {
        val error = useCases.validateLogin(login)
        val newLoginState = FieldViewState.Text(login, error?.toTextFieldError())
        _state.update { it.copy(loginState = newLoginState) }
    }

    private fun changePassword(password: String) = viewModelScope.launch(Dispatchers.Default) {
        val error = useCases.validatePasswordText(password)
        _state.update {
            val newPasswordState = it.passwordState.copy(
                text = password,
                error = error?.toTextFieldError()
            )
            it.copy(passwordState = newPasswordState)
        }
    }

    private fun togglePasswordVisibility() {
        _state.update {
            val currVisible = it.passwordState.isVisible
            val newPasswordState = it.passwordState.copy(isVisible = !currVisible)
            it.copy(passwordState = newPasswordState)
        }
    }

    private fun tryFinishEditing() = viewModelScope.launch(Dispatchers.Default) {
        val passwordDto = getCurrPasswordDto()
        val validationResult = validatePassword(passwordDto)
        if (hasValidationErrors(validationResult))
            showAllValidationErrors(validationResult)
        else
            onFinishEditing(passwordDto)
    }

    protected fun getCurrPasswordDto() = _state.value.toPasswordEditingDto()

    private fun validatePassword(passwordDto: PasswordEditingDto) = with(passwordDto) {
        useCases.validatePassword(ValidatePasswordUseCase.Params(title, login, password))
    }

    private fun hasValidationErrors(validationResult: ValidatePasswordUseCase.Result) =
        validationResult.titleError != null ||
                validationResult.loginError != null ||
                validationResult.passwordError != null

    private fun showAllValidationErrors(validationResult: ValidatePasswordUseCase.Result) {
        _state.update {
            it.copy(
                titleState = it.titleState.copy(
                    error = validationResult.titleError?.toTextFieldError()
                ),
                loginState = it.loginState.copy(
                    error = validationResult.loginError?.toTextFieldError()
                ),
                passwordState = it.passwordState.copy(
                    error = validationResult.passwordError?.toTextFieldError()
                ),
            )
        }
    }

    protected abstract suspend fun onFinishEditing(passwordDto: PasswordEditingDto)

    private fun exitScreen() {
        _state.update { it.copy(isExitFromScreen = true) }
    }

    protected fun sendExitScreenEvent() {
        sendEvent(PasswordEditingEvent.ExitScreen)
    }
}
