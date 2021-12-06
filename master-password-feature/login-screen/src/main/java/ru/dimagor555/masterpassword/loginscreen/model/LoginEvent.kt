package ru.dimagor555.masterpassword.loginscreen.model

import me.aartikov.sesame.localizedstring.LocalizedString

internal sealed class LoginEvent {
    data class OnPasswordChanged(val password: String) : LoginEvent()

    object TogglePasswordVisibility : LoginEvent()

    data class ShowError(val error: LocalizedString?) : LoginEvent()

    object LoginByPassword : LoginEvent()
    object LoginByBiometrics : LoginEvent()

    object EnableBiometricLogin : LoginEvent()

    object ExitLoginScreenWithSuccess : LoginEvent()
}
