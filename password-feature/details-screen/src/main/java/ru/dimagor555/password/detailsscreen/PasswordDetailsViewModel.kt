package ru.dimagor555.password.detailsscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.dimagor555.core.DataState
import ru.dimagor555.core.ProgressBarState
import ru.dimagor555.core.UiComponentVisibility
import ru.dimagor555.password.detailsscreen.model.*
import ru.dimagor555.password.domain.Password
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
internal class PasswordDetailsViewModel @Inject constructor(
    private val useCases: PasswordDetailsUseCases
) : ViewModel() {
    private var passwordId: Int by Delegates.notNull()

    private val _state = MutableStateFlow(PasswordDetailsViewState())
    val state = _state.asStateFlow()

    fun sendEvent(event: PasswordDetailsEvent) {
        when (event) {
            is PasswordDetailsEvent.LoadPassword -> loadPassword(event.passwordId)
            is PasswordDetailsEvent.ShowPassword -> showPassword(event.password)
            is PasswordDetailsEvent.UpdateProgressBarState -> updateProgressBarState(event.value)
            is PasswordDetailsEvent.UpdateRemoveDialogVisibility ->
                updateRemoveDialogVisibility(event.visibility)
            PasswordDetailsEvent.ToggleFavourite -> toggleFavourite()
            PasswordDetailsEvent.TogglePasswordVisibility -> togglePasswordVisibility()
            PasswordDetailsEvent.CopyPassword -> copyPassword()
            PasswordDetailsEvent.CopyLogin -> copyLogin()
            PasswordDetailsEvent.RemovePassword -> removePassword()
            PasswordDetailsEvent.OnPasswordRemoved -> onPasswordRemoved()
        }
    }

    private fun loadPassword(passwordId: Int) {
        this.passwordId = passwordId
        observePassword()
    }

    private fun observePassword() {
        useCases.observePassword(passwordId)
            .onEach { unwrapPasswordDataState(it) }
            .launchIn(viewModelScope)
    }

    private fun unwrapPasswordDataState(dataState: DataState<Password>) {
        when (dataState) {
            is DataState.Data -> {
                sendEvent(PasswordDetailsEvent.ShowPassword(dataState.data))
                sendEvent(PasswordDetailsEvent.UpdateProgressBarState(ProgressBarState.Idle))
            }
            is DataState.Nothing -> {
                sendEvent(PasswordDetailsEvent.OnPasswordRemoved)
            }
            is DataState.Loading -> {
                sendEvent(PasswordDetailsEvent.UpdateProgressBarState(ProgressBarState.Loading))
            }
        }
    }

    private fun showPassword(password: Password) {
        _state.update { it.copy(passwordViewState = password.toPasswordViewState()) }
    }

    private fun updateProgressBarState(state: ProgressBarState) {
        _state.update { it.copy(progressBarState = state) }
    }

    private fun updateRemoveDialogVisibility(visibility: UiComponentVisibility) {
        _state.update { it.copy(removeDialogVisibility = visibility) }
    }

    private fun toggleFavourite() = viewModelScope.launch {
        useCases.toggleFavourite(passwordId)
    }

    private fun togglePasswordVisibility() = viewModelScope.launch {
        val isVisible = _state.value.passwordTextViewState.isVisible
        val newPasswordTextViewState = createToggledPasswordText(isVisible)
        _state.update { it.copy(passwordTextViewState = newPasswordTextViewState) }
    }

    private suspend fun createToggledPasswordText(isVisible: Boolean) =
        if (isVisible)
            createHiddenPasswordText()
        else
            createVisiblePasswordText(useCases.decryptPassword(passwordId))

    private fun copyPassword() = viewModelScope.launch {
        useCases.copyPassword(passwordId)
    }

    private fun copyLogin() = viewModelScope.launch {
        useCases.copyLogin(passwordId)
    }

    private fun removePassword() = viewModelScope.launch {
        useCases.removePassword(passwordId)
        sendEvent(PasswordDetailsEvent.OnPasswordRemoved)
    }

    private fun onPasswordRemoved() {
        _state.update { it.copy(isPasswordRemoved = true) }
    }
}