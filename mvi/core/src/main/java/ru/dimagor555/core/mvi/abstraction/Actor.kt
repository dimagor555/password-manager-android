package ru.dimagor555.core.mvi.abstraction

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlin.properties.Delegates

abstract class Actor<State : Any, in Action : Any, Message : Any, SideEffect : Any> {
    private val _messages = MutableSharedFlow<Message>()
    internal val messages = _messages.asSharedFlow()

    private val _sideEffects = MutableSharedFlow<SideEffect>()
    internal val sideEffects = _sideEffects.asSharedFlow()

    private var getState: () -> State by Delegates.notNull()

    internal fun init(getState: () -> State) {
        this.getState = getState
    }

    abstract suspend fun onAction(action: Action)

    protected suspend fun sendMessage(msg: Message) = _messages.emit(msg)

    protected suspend fun sendSideEffect(sideEffect: SideEffect) = _sideEffects.emit(sideEffect)

    protected fun getState() = getState.invoke()
}