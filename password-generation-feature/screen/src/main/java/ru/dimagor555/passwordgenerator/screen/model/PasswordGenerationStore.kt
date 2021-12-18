package ru.dimagor555.passwordgenerator.screen.model

import me.aartikov.sesame.localizedstring.LocalizedString
import ru.dimagor555.mvicompose.abstraction.Store
import ru.dimagor555.mvicompose.implementation.SimpleActionBootstrapper
import ru.dimagor555.mvicompose.implementation.StoreImpl
import ru.dimagor555.passwordgeneration.domain.PasswordCharGroup
import ru.dimagor555.passwordgeneration.domain.PasswordLength
import ru.dimagor555.passwordgenerator.screen.model.PasswordGenerationStore.*

internal class PasswordGenerationStore :
    Store<Action, State, SideEffect> by StoreImpl(
        initialState = State(),
        actor = PasswordGenerationActor(),
        reducer = PasswordGenerationReducer(),
        bootstrapper = SimpleActionBootstrapper(Action.GeneratePassword)
    ) {

    data class State(
        val password: String = "",
        val length: PasswordLength = PasswordLength.MEDIUM,
        val selectedCharGroups: List<PasswordCharGroup> = PasswordCharGroup.values().toList()
    ) {
        val canGenerate
            get() = selectedCharGroups.isNotEmpty()
    }

    sealed class Action {
        object GeneratePassword : Action()
        data class ChooseLength(val length: PasswordLength) : Action()
        data class ToggleCharGroup(val charGroup: PasswordCharGroup) : Action()
    }

    sealed class Message {
        data class ShowPassword(val password: String) : Message()
        data class ShowLength(val length: PasswordLength) : Message()
        data class ShowSelectedCharGroups(val charGroups: List<PasswordCharGroup>) : Message()
    }

    sealed class SideEffect {
        data class ShowMessage(val message: LocalizedString) : SideEffect()
    }
}