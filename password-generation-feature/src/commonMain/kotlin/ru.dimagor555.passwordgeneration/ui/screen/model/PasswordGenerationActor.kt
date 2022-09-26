package ru.dimagor555.passwordgeneration.ui.screen.model

import dev.icerock.moko.resources.desc.desc
import ru.dimagor555.mvicompose.abstraction.Actor
import ru.dimagor555.passwordgeneration.domain.PasswordCharGroup
import ru.dimagor555.passwordgeneration.domain.PasswordGenerationParams
import ru.dimagor555.passwordgeneration.domain.PasswordLength
import ru.dimagor555.passwordgeneration.ui.screen.model.PasswordGenerationStore.*
import ru.dimagor555.res.core.MR

internal class PasswordGenerationActor(
    private val useCases: PasswordGenerationUseCases = PasswordGenerationUseCases()
) : Actor<State, Action, Message, SideEffect>() {
    override suspend fun onAction(action: Action) =
        when (action) {
            Action.GeneratePassword -> tryGeneratePassword(getState())
            is Action.ChooseLength -> chooseLength(action.length)
            is Action.ToggleCharGroup -> toggleCharGroup(action.charGroup)
        }

    private suspend fun tryGeneratePassword(state: State) {
        if (!state.canGenerate)
            showCannotGenerateMessage()
        else
            showGeneratedPassword(state)
    }

    private suspend fun showCannotGenerateMessage() {
        val message = MR.strings.can_not_generate_error.desc()
        sendSideEffect(SideEffect.ShowMessage(message))
    }

    private suspend fun showGeneratedPassword(state: State) {
        val generatedPassword = useCases.generatePassword(
            PasswordGenerationParams(state.length, state.selectedCharGroups)
        )
        sendMessage(Message.ShowPassword(generatedPassword))
    }

    private suspend fun chooseLength(length: PasswordLength) {
        sendMessage(Message.ShowLength(length))
        onAction(Action.GeneratePassword)
    }

    private suspend fun toggleCharGroup(charGroup: PasswordCharGroup) {
        val newCharGroups = useCases.toggleCharGroup(getState().selectedCharGroups, charGroup)
        sendMessage(Message.ShowSelectedCharGroups(newCharGroups))
        onAction(Action.GeneratePassword)
    }
}