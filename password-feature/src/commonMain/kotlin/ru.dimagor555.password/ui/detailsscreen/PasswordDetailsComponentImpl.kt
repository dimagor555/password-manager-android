package ru.dimagor555.password.ui.detailsscreen

import com.arkivanov.decompose.ComponentContext
import ru.dimagor555.core.presentation.MviComponentContext
import ru.dimagor555.password.ui.detailsscreen.PasswordDetailsComponent.PasswordDetailsComponentCallbacks
import ru.dimagor555.password.ui.detailsscreen.model.PasswordDetailsStore
import ru.dimagor555.password.ui.detailsscreen.model.PasswordDetailsStore.*

fun createPasswordDetailsComponent(
    componentContext: ComponentContext,
    passwordId: String,
    parentId: String,
    callbacks: PasswordDetailsComponentCallbacks,
): PasswordDetailsComponent {
    return PasswordDetailsComponentImpl(componentContext, passwordId, parentId, callbacks)
}

internal class PasswordDetailsComponentImpl constructor(
    componentContext: ComponentContext,
    val passwordId: String,
    val parentId: String,
    val callbacks: PasswordDetailsComponentCallbacks,
) : MviComponentContext<Action, State, SideEffect>(
    componentContext = componentContext,
    storeFactory = { PasswordDetailsStore() },
), PasswordDetailsComponent