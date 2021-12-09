package ru.dimagor555.masterpassword.loginscreen.model

import ru.dimagor555.ui.core.model.FieldState

internal data class LoginState(
    val passwordState: FieldState.Password = FieldState.Password(),
    val isBiometricLoginButtonVisible: Boolean = false,
    val isExitScreenWithSuccess: Boolean = false
)