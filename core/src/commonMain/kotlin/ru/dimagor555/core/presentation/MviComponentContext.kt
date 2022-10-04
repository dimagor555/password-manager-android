package ru.dimagor555.core.presentation

import com.arkivanov.decompose.ComponentContext
import ru.dimagor555.mvicompose.abstraction.Store

abstract class MviComponentContext<in Action : Any, out State : Any, out SideEffect : Any>(
    componentContext: ComponentContext,
    storeFactory: () -> Store<Action, State, SideEffect>,
) : ComponentContext by componentContext {

    val store by lazy {
        instanceKeeper.getStore {
            storeFactory()
        }
    }

    val state = store.state
    val sideEffects = store.sideEffects

    fun sendAction(action: Action) = store.sendAction(action)
}