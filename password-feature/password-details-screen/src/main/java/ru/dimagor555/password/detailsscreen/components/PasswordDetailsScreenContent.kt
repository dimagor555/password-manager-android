package ru.dimagor555.password.detailsscreen.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.dimagor555.password.detailsscreen.R
import ru.dimagor555.password.detailsscreen.model.PasswordDetailsEvent
import ru.dimagor555.password.detailsscreen.model.PasswordDetailsViewState
import ru.dimagor555.password.detailsscreen.model.PasswordViewState
import ru.dimagor555.password.ui.core.LargePaddingColumn
import ru.dimagor555.ui.core.theme.PasswordManagerTheme

@Composable
internal fun PasswordDetailsScreenContent(
    state: PasswordDetailsViewState,
    sendEvent: (PasswordDetailsEvent) -> Unit,
    showSnackbar: (String, String?) -> Unit
) {
    LargePaddingColumn(modifier = Modifier.fillMaxWidth()) {
        LoginTextRow(
            login = state.passwordViewState.login,
            onCopyLogin = { sendEvent(PasswordDetailsEvent.CopyLogin) },
            showSnackbar = showSnackbar
        )
        PasswordTextRow(
            password = state.passwordTextViewState.passwordText,
            onCopyPassword = { sendEvent(PasswordDetailsEvent.CopyPassword) },
            showSnackbar = showSnackbar
        )
        TogglePasswordVisibilityButton(
            isVisible = state.passwordTextViewState.isVisible,
            onToggleVisibility = { sendEvent(PasswordDetailsEvent.TogglePasswordVisibility) }
        )
    }
}

@Composable
private fun LoginTextRow(
    login: String,
    onCopyLogin: () -> Unit,
    showSnackbar: (String, String?) -> Unit
) {
    CopyableTextRow(
        text = login,
        headline = stringResource(R.string.login),
        buttonContentDescription = stringResource(R.string.copy_login),
        onCopy = onCopyLogin,
        showSnackbar = showSnackbar,
        snackbarMessage = stringResource(R.string.login_copied)
    )
}

@Composable
private fun PasswordTextRow(
    password: String,
    onCopyPassword: () -> Unit,
    showSnackbar: (String, String?) -> Unit
) {
    CopyableTextRow(
        text = password,
        headline = stringResource(R.string.password),
        buttonContentDescription = stringResource(R.string.copy_password),
        onCopy = onCopyPassword,
        showSnackbar = showSnackbar,
        snackbarMessage = stringResource(R.string.password_copied)
    )
}

@Preview("Password details")
@Preview("Password details (ru)", locale = "ru")
@Preview("Password details (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PasswordDetailsScreenContentPreview() {
    PasswordManagerTheme {
        Surface {
            Box(modifier = Modifier.padding(8.dp)) {
                PasswordDetailsScreenContent(
                    state = PasswordDetailsViewState(
                        passwordViewState = PasswordViewState(login = "Username1234")
                    ),
                    sendEvent = {},
                    showSnackbar = { _, _ -> }
                )
            }
        }
    }
}