package ru.dimagor555.masterpassword.ui.loginscreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.dimagor555.masterpassword.ui.core.PasswordErrorIndicator
import ru.dimagor555.masterpassword.ui.loginscreen.biometric.BiometricLoginDialog
import ru.dimagor555.masterpassword.ui.loginscreen.biometric.OnCanUseBiometricLogin
import ru.dimagor555.masterpassword.ui.loginscreen.model.LoginStore.Action
import ru.dimagor555.masterpassword.ui.loginscreen.model.LoginStore.State
import ru.dimagor555.res.core.MR
import ru.dimagor555.ui.core.component.textfield.PasswordInputField
import ru.dimagor555.ui.core.model.isError
import ru.dimagor555.ui.core.theme.PasswordManagerTheme
import ru.dimagor555.ui.core.util.Preview
import ru.dimagor555.ui.core.util.resolve
import ru.dimagor555.ui.core.util.stringResource

@Composable
fun LoginScreen(component: LoginComponent) {
    component as LoginComponentImpl

    val state by component.state.collectAsState()

    LoginScreenContent(
        state = state,
        sendAction = component::sendAction
    )
    OnCanUseBiometricLogin { component.sendAction(Action.EnableBiometricLogin) }
    LaunchedEffect(key1 = state.isExitScreenWithSuccess) {
        if (state.isExitScreenWithSuccess)
            component.onSuccessfulLogin()
    }
}

@Composable
private fun LoginScreenContent(
    state: State,
    sendAction: (Action) -> Unit
) {
    LoginScreenColumn {
        PasswordErrorIndicator(isError = state.password.isError)
        Spacer(modifier = Modifier.height(16.dp))
        PasswordInputField(state = state, sendAction = sendAction)
        LoginButton(
            enabled = state.canLogin,
            onLoginByPassword = { sendAction(Action.LoginByPassword) }
        )
        if (state.canUseBiometricLogin)
            BiometricLoginButton(
                enabled = state.canLogin,
                onSuccessfulLogin = { sendAction(Action.ExitScreenWithSuccess) }
            )
    }
}

@Composable
private fun LoginScreenColumn(content: @Composable ColumnScope.() -> Unit) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 12.dp)
                .padding(top = 48.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            content = content
        )
    }
}

@Composable
private fun PasswordInputField(
    state: State,
    sendAction: (Action) -> Unit
) {
    PasswordInputField(
        value = state.password.text,
        onValueChange = { sendAction(Action.ChangePassword(it)) },
        isPasswordVisible = state.password.isVisible,
        onTogglePasswordVisibility = { sendAction(Action.TogglePasswordVisibility) },
        error = state.password.error?.resolve(),
        keyboardActions = KeyboardActions(onDone = { sendAction(Action.LoginByPassword) })
    )
}

@Composable
private fun LoginButton(
    enabled: Boolean,
    onLoginByPassword: () -> Unit
) {
    Button(
        onClick = onLoginByPassword,
        enabled = enabled
    ) {
        Text(text = stringResource(MR.strings.login_button_text))
    }
}

@Composable
private fun BiometricLoginButton(
    enabled: Boolean,
    onSuccessfulLogin: () -> Unit
) {
    var showBiometricLoginDialog by remember { mutableStateOf(false) }
    TextButton(
        enabled = enabled,
        onClick = { showBiometricLoginDialog = true }
    ) {
        Icon(imageVector = Icons.Default.Fingerprint, contentDescription = null)
        Text(text = stringResource(MR.strings.biometry_login))
    }
    if (showBiometricLoginDialog)
        BiometricLoginDialog(
            onSuccess = onSuccessfulLogin,
            onDismiss = { showBiometricLoginDialog = false }
        )
}

@Preview
@Composable
private fun LoginScreenPreview() {
    PasswordManagerTheme {
        LoginScreenContent(
            state = State(canUseBiometricLogin = true),
            sendAction = {}
        )
    }
}
