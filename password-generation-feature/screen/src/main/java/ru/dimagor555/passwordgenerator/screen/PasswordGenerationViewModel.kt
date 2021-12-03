package ru.dimagor555.passwordgenerator.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.dimagor555.passwordgeneration.domain.CharGroupsSelection
import ru.dimagor555.passwordgeneration.domain.PasswordCharGroup
import ru.dimagor555.passwordgeneration.domain.PasswordGenerationParams
import ru.dimagor555.passwordgeneration.domain.PasswordLength
import ru.dimagor555.passwordgenerator.screen.model.PasswordGenerationEvent
import ru.dimagor555.passwordgenerator.screen.model.PasswordGenerationViewState
import javax.inject.Inject

@HiltViewModel
internal class PasswordGenerationViewModel @Inject constructor(
    private val useCase: PasswordGenerationUseCases
) : ViewModel() {
    private val _state = MutableStateFlow(PasswordGenerationViewState())
    val state = _state.asStateFlow()

    init {
        sendEvent(PasswordGenerationEvent.GeneratePassword)
    }

    fun sendEvent(event: PasswordGenerationEvent) {
        when (event) {
            is PasswordGenerationEvent.GeneratePassword -> onGeneratePassword()
            is PasswordGenerationEvent.OnLengthChanged -> changeLength(event.length)
            is PasswordGenerationEvent.OnToggleCharGroup -> toggleCharGroup(event.charGroup)
        }
    }

    private fun onGeneratePassword() = viewModelScope.launch {
        _state.update {
            if (!it.isGenerateButtonEnabled)
                return@launch
            it.copy(generatedPassword = generatePassword(it))
        }
    }

    private suspend fun generatePassword(state: PasswordGenerationViewState) =
        useCase.generatePassword(
            PasswordGenerationParams(
                length = state.length,
                charGroups = state.selectedCharGroups
            )
        )

    private fun changeLength(length: PasswordLength) {
        _state.update { it.copy(length = length) }
        sendEvent(PasswordGenerationEvent.GeneratePassword)
    }

    private fun toggleCharGroup(charGroup: PasswordCharGroup) {
        _state.update {
            val charGroupsSelection = CharGroupsSelection(it.selectedCharGroups)
            charGroupsSelection.toggleCharGroup(charGroup)
            it.copy(
                selectedCharGroups = charGroupsSelection.getSelectedGroups(),
                isGenerateButtonEnabled = charGroupsSelection.hasSelectedGroups()
            )
        }
        sendEvent(PasswordGenerationEvent.GeneratePassword)
    }
}