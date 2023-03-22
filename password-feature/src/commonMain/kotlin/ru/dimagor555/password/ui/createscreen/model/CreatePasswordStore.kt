package ru.dimagor555.password.ui.createscreen.model

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.dimagor555.mvicompose.abstraction.Actor
import ru.dimagor555.mvicompose.abstraction.Reducer
import ru.dimagor555.mvicompose.abstraction.Store
import ru.dimagor555.mvicompose.implementation.StoreImpl
import ru.dimagor555.password.domain.password.PasswordFields
import ru.dimagor555.password.ui.createscreen.model.CreatePasswordStore.Action
import ru.dimagor555.password.ui.createscreen.model.CreatePasswordStore.State
import ru.dimagor555.password.usecase.password.single.CreatePasswordUseCase

internal class CreatePasswordStore : Store<Action, State, Nothing> by StoreImpl(
    initialState = State(),
    actor = ActorImpl(),
    reducer = ReducerImpl(),
) {

    data class State(
        val isCreatingStarted: Boolean = false,
        val isExitScreen: Boolean = false,
    )

    sealed interface Action {
        data class CreatePassword(
            val parentId: String,
            val passwordFields: PasswordFields,
        ) : Action
    }

    sealed interface Message {
        object StartCreating : Message

        object ExitScreen : Message
    }

    class ActorImpl : Actor<State, Action, Message, Nothing>(), KoinComponent {

        private val useCases: CreatePasswordUseCases by inject()

        override suspend fun onAction(action: Action) {
            when (action) {
                is Action.CreatePassword -> createPassword(action.parentId, action.passwordFields)
            }
        }

        private suspend fun createPassword(parentId: String, passwordFields: PasswordFields) {
            sendMessage(Message.StartCreating)
            if (!getState().isCreatingStarted)
                return
            useCases.createPassword(
                CreatePasswordUseCase.Params(
                    parentId = parentId,
                    fields = passwordFields,
                )
            )
            sendMessage(Message.ExitScreen)
        }
    }

    internal class ReducerImpl : Reducer<State, Message> {
        override fun State.reduce(msg: Message) =
            when (msg) {
                Message.StartCreating -> copy(isCreatingStarted = true)
                Message.ExitScreen -> copy(isExitScreen = true)
            }
    }
}