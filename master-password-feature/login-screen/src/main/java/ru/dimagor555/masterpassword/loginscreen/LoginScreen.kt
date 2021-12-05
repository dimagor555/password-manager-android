package ru.dimagor555.masterpassword.loginscreen

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.dimagor555.masterpassword.loginscreen.components.BiometricLoginDialog
import ru.dimagor555.masterpassword.loginscreen.model.LoginEvent
import ru.dimagor555.masterpassword.loginscreen.model.LoginViewState
import ru.dimagor555.masterpassword.ui.core.PasswordErrorIndicator
import ru.dimagor555.ui.core.components.PasswordInputField
import ru.dimagor555.ui.core.components.SimpleButton
import ru.dimagor555.ui.core.theme.PasswordManagerTheme

@Composable
fun LoginScreen(onSuccessfulLogin: () -> Unit) {
    val viewModel: LoginViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    LoginScreen(
        state = state,
        sendEvent = viewModel::sendEvent,
        onSuccessfulLogin = onSuccessfulLogin
    )
}

@Composable
private fun LoginScreen(
    state: LoginViewState,
    sendEvent: (LoginEvent) -> Unit,
    onSuccessfulLogin: () -> Unit
) {
    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            PasswordErrorIndicator(state = state.errorIndicatorState)
            Spacer(modifier = Modifier.height(16.dp))
            PasswordInputField(state = state, sendEvent = sendEvent)
            LoginButton(onLoginByPassword = { sendEvent(LoginEvent.LoginByPassword) })
            if (state.isBiometricLoginButtonVisible)
                BiometricLoginButton(
                    onSuccessfulLogin = { sendEvent(LoginEvent.ExitLoginScreenWithSuccess) }
                )
        }
    }
    LaunchedEffect(key1 = state.isExitScreenWithSuccess) {
        if (state.isExitScreenWithSuccess)
            onSuccessfulLogin()
    }
}

@Composable
private fun PasswordInputField(
    state: LoginViewState,
    sendEvent: (LoginEvent) -> Unit
) {
    PasswordInputField(
        value = state.password,
        onValueChange = { sendEvent(LoginEvent.OnPasswordChanged(it)) },
        isPasswordVisible = state.isPasswordVisible,
        onTogglePasswordVisibility = { sendEvent(LoginEvent.TogglePasswordVisibility) },
        error = state.error?.resolve(LocalContext.current) as String?,
        keyboardActions = KeyboardActions(onDone = { sendEvent(LoginEvent.LoginByPassword) })
    )
}

@Composable
private fun LoginButton(onLoginByPassword: () -> Unit) {
    SimpleButton(
        text = stringResource(R.string.login),
        onClick = onLoginByPassword
    )
}

@Composable
private fun BiometricLoginButton(onSuccessfulLogin: () -> Unit) {
    var showBiometricLoginDialog by remember { mutableStateOf(false) }
    TextButton(onClick = { showBiometricLoginDialog = true }) {
        Icon(imageVector = Icons.Default.Fingerprint, contentDescription = null)
        Text(text = stringResource(R.string.biometry_login))
    }
    if (showBiometricLoginDialog)
        BiometricLoginDialog(
            onSuccess = onSuccessfulLogin,
            onDismiss = { showBiometricLoginDialog = false }
        )
}

@Preview("LoginScreen")
@Preview("LoginScreen (ru)", locale = "ru")
@Preview("LoginScreen (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun LoginScreenPreview() {
    PasswordManagerTheme {
        LoginScreen(
            state = LoginViewState(isBiometricLoginButtonVisible = true),
            sendEvent = {},
            onSuccessfulLogin = {}
        )
    }
}
