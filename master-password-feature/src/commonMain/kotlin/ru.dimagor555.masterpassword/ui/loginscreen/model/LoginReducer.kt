package ru.dimagor555.masterpassword.ui.loginscreen.model

import ru.dimagor555.masterpassword.ui.loginscreen.model.LoginStore.Message
import ru.dimagor555.masterpassword.ui.loginscreen.model.LoginStore.State
import ru.dimagor555.mvicompose.abstraction.Reducer
import ru.dimagor555.ui.core.model.toggleVisibility

internal class LoginReducer : Reducer<State, Message> {
    override fun State.reduce(msg: Message) =
        when (msg) {
            is Message.ShowPassword -> copy(password = password.copy(text = msg.password))
            is Message.ShowError -> copy(password = password.copy(error = msg.error))
            Message.TogglePasswordVisibility -> copy(password = password.toggleVisibility())
            Message.EnableLogin -> copy(canLogin = true)
            Message.DisableLogin -> copy(canLogin = false)
            Message.EnableBiometricLogin -> copy(canUseBiometricLogin = true)
            Message.ExitScreenWithSuccess -> copy(isExitScreenWithSuccess = true)
        }
}