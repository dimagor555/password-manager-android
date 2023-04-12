package ru.dimagor555.masterpassword.ui.loginscreen

import com.arkivanov.decompose.ComponentContext
import ru.dimagor555.core.presentation.MviComponentContext
import ru.dimagor555.masterpassword.ui.loginscreen.store.LoginStore
import ru.dimagor555.masterpassword.ui.loginscreen.store.LoginStore.Action
import ru.dimagor555.masterpassword.ui.loginscreen.store.LoginStore.State

fun createLoginComponent(
    componentContext: ComponentContext,
    onSuccessfulLogin: () -> Unit,
): LoginComponent =
    LoginComponentImpl(componentContext, onSuccessfulLogin)

internal class LoginComponentImpl constructor(
    componentContext: ComponentContext,
    val onSuccessfulLogin: () -> Unit,
) : MviComponentContext<Action, State, Nothing>(
    componentContext = componentContext,
    storeFactory = { LoginStore() },
), LoginComponent