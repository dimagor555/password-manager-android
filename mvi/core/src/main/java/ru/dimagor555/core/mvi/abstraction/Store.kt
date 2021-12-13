package ru.dimagor555.core.mvi.abstraction

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface Store<in Action : Any, out State : Any, out SideEffect> {
    val state: StateFlow<State>
    val sideEffects: SharedFlow<SideEffect>

    fun sendAction(action: Action)

    fun init(scope: CoroutineScope)
}