package ru.dimagor555.masterpassword.ui.loginscreen

import com.arkivanov.decompose.ComponentContext
import ru.dimagor555.core.presentation.MviComponentContext
import ru.dimagor555.masterpassword.ui.loginscreen.model.LoginStore
import ru.dimagor555.masterpassword.ui.loginscreen.model.LoginStore.Action
import ru.dimagor555.masterpassword.ui.loginscreen.model.LoginStore.State

fun createLoginComponent(
    componentContext: ComponentContext,
    onSuccessfulLogin: () -> Unit,
): Login =
    LoginComponent(componentContext, onSuccessfulLogin)

internal class LoginComponent constructor(
    componentContext: ComponentContext,
    private val onSuccessfulLogin: () -> Unit,
) : MviComponentContext<Action, State, Nothing>(
    componentContext = componentContext,
    storeFactory = { LoginStore() },
), Login {

    fun successfulLogin() {
        onSuccessfulLogin()
    }

}