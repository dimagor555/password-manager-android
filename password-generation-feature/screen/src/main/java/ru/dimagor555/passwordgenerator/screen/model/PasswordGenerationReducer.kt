package ru.dimagor555.passwordgenerator.screen.model

import ru.dimagor555.mvicompose.abstraction.Reducer
import ru.dimagor555.passwordgenerator.screen.model.PasswordGenerationStore.Message
import ru.dimagor555.passwordgenerator.screen.model.PasswordGenerationStore.State

internal class PasswordGenerationReducer : Reducer<State, Message> {
    override fun State.reduce(msg: Message) =
        when (msg) {
            is Message.ShowPassword -> copy(password = msg.password)
            is Message.ShowLength -> copy(length = msg.length)
            is Message.ShowSelectedCharGroups -> copy(selectedCharGroups = msg.charGroups)
        }
}