package ru.dimagor555.password.ui.listscreen

import com.arkivanov.decompose.ComponentContext
import ru.dimagor555.core.presentation.MviComponentContext
import ru.dimagor555.password.ui.listscreen.PasswordListComponent.PasswordListComponentCallbacks
import ru.dimagor555.password.ui.listscreen.model.PasswordListStore
import ru.dimagor555.password.ui.listscreen.model.PasswordListStore.*

fun createPasswordListComponent(
    componentContext: ComponentContext,
    callbacks: PasswordListComponentCallbacks,
): PasswordListComponent {
    return PasswordListComponentImpl(componentContext, callbacks)
}

internal class PasswordListComponentImpl constructor(
    componentContext: ComponentContext,
    val callbacks: PasswordListComponentCallbacks,
) : MviComponentContext<Action, State, SideEffect>(
    componentContext = componentContext,
    storeFactory = { PasswordListStore() },
), PasswordListComponent