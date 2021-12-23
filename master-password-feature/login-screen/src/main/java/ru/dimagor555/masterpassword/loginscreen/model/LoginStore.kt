package ru.dimagor555.masterpassword.loginscreen.model

import me.aartikov.sesame.localizedstring.LocalizedString
import ru.dimagor555.masterpassword.loginscreen.model.LoginStore.Action
import ru.dimagor555.masterpassword.loginscreen.model.LoginStore.State
import ru.dimagor555.mvicompose.abstraction.Store
import ru.dimagor555.mvicompose.implementation.StoreImpl
import ru.dimagor555.ui.core.model.FieldState

internal class LoginStore(useCases: LoginUseCases) :
    Store<Action, State, Nothing> by StoreImpl(
        initialState = State(),
        actor = LoginActor(useCases),
        reducer = LoginReducer()
    ) {

    data class State(
        val password: FieldState.Password = FieldState.Password(),
        val isBiometricLoginEnabled: Boolean = false,
        val isExitScreenWithSuccess: Boolean = false
    )

    sealed class Action {
        data class ChangePassword(val password: String) : Action()
        object TogglePasswordVisibility : Action()

        object LoginByPassword : Action()

        object EnableBiometricLogin : Action()

        object ExitScreenWithSuccess : Action()
    }

    sealed class Message {
        data class ShowPassword(val password: String) : Message()
        data class ShowError(val error: LocalizedString?) : Message()
        object TogglePasswordVisibility : Message()

        object EnableBiometricLogin : Message()

        object ExitScreenWithSuccess : Message()
    }
}