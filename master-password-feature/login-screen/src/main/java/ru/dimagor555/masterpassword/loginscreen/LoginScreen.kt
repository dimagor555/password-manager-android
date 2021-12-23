package ru.dimagor555.masterpassword.loginscreen

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Surface
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
import ru.dimagor555.masterpassword.loginscreen.biometric.BiometricLoginDialog
import ru.dimagor555.masterpassword.loginscreen.biometric.OnCanUseBiometricLogin
import ru.dimagor555.masterpassword.loginscreen.model.LoginStore.Action
import ru.dimagor555.masterpassword.loginscreen.model.LoginStore.State
import ru.dimagor555.masterpassword.ui.core.PasswordErrorIndicator
import ru.dimagor555.ui.core.component.button.SimpleButton
import ru.dimagor555.ui.core.component.textfield.PasswordInputField
import ru.dimagor555.ui.core.model.isError
import ru.dimagor555.ui.core.theme.PasswordManagerTheme

@Composable
fun LoginScreen(onSuccessfulLogin: () -> Unit) {
    val viewModel: LoginViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    LoginScreenContent(
        state = state,
        sendAction = viewModel::sendAction
    )
    OnCanUseBiometricLogin { viewModel.sendAction(Action.EnableBiometricLogin) }
    LaunchedEffect(key1 = state.isExitScreenWithSuccess) {
        if (state.isExitScreenWithSuccess)
            onSuccessfulLogin()
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
        LoginButton(onLoginByPassword = { sendAction(Action.LoginByPassword) })
        if (state.isBiometricLoginEnabled)
            BiometricLoginButton(
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
        error = state.password.error?.resolve(LocalContext.current) as String?,
        keyboardActions = KeyboardActions(onDone = { sendAction(Action.LoginByPassword) })
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
        LoginScreenContent(
            state = State(isBiometricLoginEnabled = true),
            sendAction = {}
        )
    }
}
