package ru.dimagor555.password.detailsscreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.dimagor555.core.DataState
import ru.dimagor555.core.ProgressBarState
import ru.dimagor555.core.UiComponentVisibility
import ru.dimagor555.password.detailsscreen.model.*
import ru.dimagor555.password.domain.Password
import javax.inject.Inject

@HiltViewModel
internal class PasswordDetailsViewModel @Inject constructor(
    private val useCases: PasswordDetailsUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val passwordId = savedStateHandle.get<Int>("passwordId")
        ?: error("passwordId argument is not passed")

    private val _state = MutableStateFlow(PasswordDetailsViewState())
    val state = _state.asStateFlow()

    init {
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

    fun sendEvent(event: PasswordDetailsEvent) {
        when (event) {
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

    private fun showPassword(password: Password) {
        _state.update { it.copy(passwordViewState = password.toPasswordViewState()) }
    }

    private fun updateProgressBarState(state: ProgressBarState) {
        _state.update { it.copy(progressBarState = state) }
    }

    private fun updateRemoveDialogVisibility(visibility: UiComponentVisibility) {
        _state.update { it.copy(removeDialogVisibility = visibility) }
    }

    private fun toggleFavourite() = viewModelScope.launch(Dispatchers.Default) {
        useCases.toggleFavourite(passwordId)
    }

    private fun togglePasswordVisibility() = viewModelScope.launch(Dispatchers.Default) {
        val isVisible = _state.value.passwordTextViewState.isVisible
        val newPasswordTextViewState = createToggledPasswordText(isVisible)
        _state.update { it.copy(passwordTextViewState = newPasswordTextViewState) }
    }

    private suspend fun createToggledPasswordText(isVisible: Boolean) =
        if (isVisible)
            createHiddenPasswordText()
        else
            createVisiblePasswordText(useCases.decryptPassword(passwordId))

    private fun copyPassword() = viewModelScope.launch(Dispatchers.Default) {
        useCases.copyPassword(passwordId)
    }

    private fun copyLogin() = viewModelScope.launch(Dispatchers.Default) {
        useCases.copyLogin(passwordId)
    }

    private fun removePassword() = viewModelScope.launch(Dispatchers.Default) {
        useCases.removePassword(passwordId)
        sendEvent(PasswordDetailsEvent.OnPasswordRemoved)
    }

    private fun onPasswordRemoved() {
        _state.update { it.copy(isPasswordRemoved = true) }
    }
}