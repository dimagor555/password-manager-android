package ru.dimagor555.masterpassword.loginscreen.model

import me.aartikov.sesame.localizedstring.LocalizedString
import ru.dimagor555.masterpassword.ui.core.model.PasswordErrorIndicatorState

internal data class LoginViewState(
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val error: LocalizedString? = null,
    val errorIndicatorState: PasswordErrorIndicatorState = PasswordErrorIndicatorState.NoError,
    val isBiometricLoginButtonVisible: Boolean = false,
    val isExitScreenWithSuccess: Boolean = false
)