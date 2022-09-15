package ru.dimagor555.masterpassword.ui.loginscreen

import ru.dimagor555.masterpassword.ui.loginscreen.model.LoginStore
import ru.dimagor555.masterpassword.ui.loginscreen.model.LoginStore.Action
import ru.dimagor555.masterpassword.ui.loginscreen.model.LoginStore.State
import ru.dimagor555.masterpassword.ui.loginscreen.model.LoginUseCases
import ru.dimagor555.mvicompose.android.MviViewModel

internal class LoginViewModel(
    useCases: LoginUseCases
) : MviViewModel<Action, State, Nothing>(
    store = LoginStore(useCases)
)