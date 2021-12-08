package ru.dimagor555.masterpassword.loginscreen.model

import ru.dimagor555.masterpassword.ui.core.model.PasswordErrorIndicatorState
import ru.dimagor555.ui.core.model.FieldState

internal data class LoginViewState(
    val passwordState: FieldState.Password = FieldState.Password(),
    val errorIndicatorState: PasswordErrorIndicatorState = PasswordErrorIndicatorState.NoError,
    val isBiometricLoginButtonVisible: Boolean = false,
    val isExitScreenWithSuccess: Boolean = false
)