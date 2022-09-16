package ru.dimagor555.password.ui.commoneditscreen.model

import me.aartikov.sesame.localizedstring.LocalizedString
import ru.dimagor555.mvicompose.abstraction.Store
import ru.dimagor555.mvicompose.implementation.StoreImpl
import ru.dimagor555.password.ui.commoneditscreen.model.CommonEditPasswordStore.*
import ru.dimagor555.ui.core.model.FieldState

class CommonEditPasswordStore(
    useCases: CommonEditPasswordUseCases
) : Store<Action, State, SideEffect> by StoreImpl(
    initialState = State(),
    actor = CommonEditPasswordActor(useCases),
    reducer = CommonEditPasswordReducer()
) {

    internal data class State(
        val fieldsByTypes: Map<FieldType, FieldState> = initialFieldsByTypes
    ) {
        val title
            get() = fieldsByTypes[FieldType.Title]!! as FieldState.Text
        val login
            get() = fieldsByTypes[FieldType.Login]!! as FieldState.Text
        val password
            get() = fieldsByTypes[FieldType.Password]!! as FieldState.Password

        enum class FieldType(val initialFieldState: FieldState) {
            Title(FieldState.Text()),
            Login(FieldState.Text()),
            Password(FieldState.Password())
        }

        companion object {
            private val initialFieldsByTypes = mapOf(
                *FieldType.values()
                    .map { fieldType -> fieldType to fieldType.initialFieldState }
                    .toTypedArray()
            )
        }
    }

    sealed interface Action {
        object Validate : Action

        data class ChangeTitle(val title: String) : Action
        data class ChangeLogin(val login: String) : Action
        data class ChangePassword(val password: String) : Action

        object TogglePasswordVisibility : Action, Message
    }

    internal sealed interface Message {
        data class ShowFieldText(
            val fieldType: State.FieldType,
            val text: String
        ) : Message

        data class ShowFieldError(
            val fieldType: State.FieldType,
            val error: LocalizedString?
        ) : Message
    }

    sealed interface SideEffect {
        data class ValidationSucceed(
            val title: String,
            val login: String,
            val password: String
        ) : SideEffect

        object ValidationFailed : SideEffect
    }
}