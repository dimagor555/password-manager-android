package ru.dimagor555.masterpassword.ui.loginscreen.model

import dev.icerock.moko.resources.desc.StringDesc
import ru.dimagor555.masterpassword.ui.loginscreen.model.LoginStore.Action
import ru.dimagor555.masterpassword.ui.loginscreen.model.LoginStore.State
import ru.dimagor555.mvicompose.abstraction.Store
import ru.dimagor555.mvicompose.implementation.StoreImpl
import ru.dimagor555.ui.core.model.FieldState

internal class LoginStore :
    Store<Action, State, Nothing> by StoreImpl(
        initialState = State(),
        actor = LoginActor(),
        reducer = LoginReducer()
    ) {

    data class State(
        val password: FieldState.Password = FieldState.Password(),
        val canLogin: Boolean = true,
        val canUseBiometricLogin: Boolean = false,
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
        data class ShowError(val error: StringDesc?) : Message()
        object TogglePasswordVisibility : Message()

        object EnableLogin : Message()
        object DisableLogin : Message()

        object EnableBiometricLogin : Message()

        object ExitScreenWithSuccess : Message()
    }
}