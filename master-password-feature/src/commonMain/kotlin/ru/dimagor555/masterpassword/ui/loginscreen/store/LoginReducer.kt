package ru.dimagor555.masterpassword.ui.loginscreen.store

import ru.dimagor555.masterpassword.ui.loginscreen.store.LoginStore.Message
import ru.dimagor555.masterpassword.ui.loginscreen.store.LoginStore.State
import ru.dimagor555.mvicompose.abstraction.Reducer
import ru.dimagor555.ui.core.model.toggleVisibility

internal class LoginReducer : Reducer<State, Message> {
    override fun State.reduce(msg: Message) =
        when (msg) {
            is Message.ShowPassword -> copy(password = password.copy(text = msg.password))
            is Message.ShowError -> copy(password = password.copy(error = msg.error))
            is Message.TogglePasswordVisibility -> copy(password = password.toggleVisibility())
            is Message.EnableLogin -> copy(canLogin = true)
            is Message.DisableLogin -> copy(canLogin = false)
            is Message.EnableBiometricLogin -> copy(canUseBiometricLogin = true)
            is Message.ExitScreenWithSuccess -> copy(isExitScreenWithSuccess = true)
        }
}