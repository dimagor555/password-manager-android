package ru.dimagor555.masterpassword.loginscreen

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.dimagor555.masterpassword.loginscreen.model.LoginStore
import ru.dimagor555.masterpassword.loginscreen.model.LoginStore.Action
import ru.dimagor555.masterpassword.loginscreen.model.LoginStore.State
import ru.dimagor555.masterpassword.loginscreen.model.LoginUseCases
import ru.dimagor555.mvicompose.android.MviViewModel
import javax.inject.Inject

@HiltViewModel
internal class LoginViewModel @Inject constructor(
    useCases: LoginUseCases
) : MviViewModel<Action, State, Nothing>(
    store = LoginStore(useCases)
)