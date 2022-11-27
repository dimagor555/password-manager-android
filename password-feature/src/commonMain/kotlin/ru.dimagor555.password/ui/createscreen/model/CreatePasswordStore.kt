package ru.dimagor555.password.ui.createscreen.model

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.dimagor555.mvicompose.abstraction.Actor
import ru.dimagor555.mvicompose.abstraction.Reducer
import ru.dimagor555.mvicompose.abstraction.Store
import ru.dimagor555.mvicompose.implementation.StoreImpl
import ru.dimagor555.password.ui.createscreen.model.CreatePasswordStore.Action
import ru.dimagor555.password.ui.createscreen.model.CreatePasswordStore.State
import ru.dimagor555.password.usecase.CreatePasswordUseCase

internal class CreatePasswordStore : Store<Action, State, Nothing> by StoreImpl(
    initialState = State(),
    actor = ActorImpl(),
    reducer = ReducerImpl()
) {

    data class State(
        val isCreatingStarted: Boolean = false,
        val isExitScreen: Boolean = false
    )

    sealed interface Action {
        data class CreatePassword(
            val title: String,
            val login: String,
            val password: String
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
                is Action.CreatePassword -> createPassword(action.title, action.login, action.password)
            }
        }

        private suspend fun createPassword(title: String, login: String, password: String) {
            if (getState().isCreatingStarted)
                return
            sendMessage(Message.StartCreating)
            useCases.createPassword(CreatePasswordUseCase.Params(title, login, password))
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