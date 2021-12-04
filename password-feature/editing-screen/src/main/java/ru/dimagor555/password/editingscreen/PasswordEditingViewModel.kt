package ru.dimagor555.password.editingscreen

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.dimagor555.core.UiComponentVisibility
import ru.dimagor555.password.editingcore.CommonPasswordEditingUseCases
import ru.dimagor555.password.editingcore.CommonPasswordEditingViewModel
import ru.dimagor555.password.editingcore.model.PasswordEditingDto
import ru.dimagor555.password.editingscreen.model.PasswordEditingEvent
import ru.dimagor555.password.editingscreen.model.PasswordEditingViewState
import ru.dimagor555.password.usecase.UpdatePasswordUseCase
import javax.inject.Inject

@HiltViewModel
internal class PasswordEditingViewModel @Inject constructor(
    private val useCases: PasswordEditingUseCases,
    commonUseCases: CommonPasswordEditingUseCases,
    savedStateHandle: SavedStateHandle
) : CommonPasswordEditingViewModel(commonUseCases) {
    private val passwordId = savedStateHandle.get<Int>("passwordId")
        ?: error("passwordId argument is not passed")

    private val _state = MutableStateFlow(PasswordEditingViewState())
    val state = _state.asStateFlow()

    private lateinit var initialPasswordDto: PasswordEditingDto

    override suspend fun getInitialPasswordDto(): PasswordEditingDto {
        createInitialPasswordDto()
        return initialPasswordDto
    }

    // TODO: 04.12.2021 In my opinion, usecase should handle this logic
    private suspend fun createInitialPasswordDto() {
        val password = useCases.getPassword(passwordId)
        val decryptedPassword = useCases.decryptPassword(passwordId)
        initialPasswordDto = PasswordEditingDto(password.title, password.login, decryptedPassword)
    }

    override suspend fun onFinishEditing(passwordDto: PasswordEditingDto) {
        if (passwordDto != initialPasswordDto)
            updatePassword(passwordDto)
        sendExitScreenEvent()
    }

    private suspend fun updatePassword(passwordDto: PasswordEditingDto) = with(passwordDto) {
        useCases.updatePassword(
            UpdatePasswordUseCase.Params(passwordId, title, login, password)
        )
    }

    fun sendEvent(event: PasswordEditingEvent) {
        when (event) {
            is PasswordEditingEvent.UpdateSaveDialogVisibility ->
                updateSaveDialogVisibility(event.visibility)
            PasswordEditingEvent.OnExitScreenRequest ->
                onExitScreenRequest()
        }
    }

    private fun updateSaveDialogVisibility(visibility: UiComponentVisibility) {
        _state.update { it.copy(saveDialogVisibility = visibility) }
    }

    private fun onExitScreenRequest() {
        if (getCurrPasswordDto() == initialPasswordDto)
            sendExitScreenEvent()
        else
            sendEvent(PasswordEditingEvent.UpdateSaveDialogVisibility(UiComponentVisibility.Show))
    }
}
