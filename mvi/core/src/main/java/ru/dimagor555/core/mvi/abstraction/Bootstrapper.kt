package ru.dimagor555.core.mvi.abstraction

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

abstract class Bootstrapper<Action : Any, Message : Any> {
    private val _actions = MutableSharedFlow<Action>()
    internal val actions = _actions.asSharedFlow()

    private val _messages = MutableSharedFlow<Message>()
    internal val messages = _messages.asSharedFlow()

    abstract fun init(scope: CoroutineScope)

    protected suspend fun sendActon(action: Action) {
        _actions.emit(action)
    }

    protected suspend fun sendMessage(msg: Message) {
        _messages.emit(msg)
    }
}