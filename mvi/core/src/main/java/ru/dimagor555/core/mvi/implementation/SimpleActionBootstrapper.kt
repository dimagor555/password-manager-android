package ru.dimagor555.core.mvi.implementation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.dimagor555.core.mvi.abstraction.Bootstrapper

class SimpleActionBootstrapper<Action : Any, Message : Any>(
    private vararg val initialActions: Action
) : Bootstrapper<Action, Message>() {
    override fun init(scope: CoroutineScope) {
        scope.launch {
            initialActions.forEach { sendActon(it) }
        }
    }
}