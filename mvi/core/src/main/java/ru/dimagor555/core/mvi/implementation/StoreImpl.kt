package ru.dimagor555.core.mvi.implementation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.dimagor555.core.mvi.abstraction.Actor
import ru.dimagor555.core.mvi.abstraction.Bootstrapper
import ru.dimagor555.core.mvi.abstraction.Reducer
import ru.dimagor555.core.mvi.abstraction.Store
import kotlin.properties.Delegates

class StoreImpl<in Action : Any, in Message : Any, out State : Any, out SideEffect : Any>(
    initialState: State,
    private val actor: Actor<State, Action, Message, SideEffect>,
    private val reducer: Reducer<State, Message>,
    private val bootstrapper: Bootstrapper<Action, Message>? = null
) : Store<Action, State, SideEffect> {
    private val _state = MutableStateFlow(initialState)
    override val state = _state.asStateFlow()

    override val sideEffects = actor.sideEffects

    private var scope: CoroutineScope by Delegates.notNull()

    override fun init(scope: CoroutineScope) {
        this.scope = scope
        initActor()
        initBootstrapper()
    }

    private fun initActor() {
        actor.init(getState = { _state.value })
        actor.messages.onEach(::onMessage).launchIn(scope)
    }

    private fun initBootstrapper() = bootstrapper?.run {
        actions.onEach(::sendAction).launchIn(scope)
        messages.onEach(::onMessage).launchIn(scope)
        scope.launch { init(scope) }
    }

    override fun sendAction(action: Action) {
        scope.launch {
            actor.onAction(action)
        }
    }

    private fun onMessage(msg: Message) = with(reducer) {
        _state.update { it.reduce(msg) }
    }
}