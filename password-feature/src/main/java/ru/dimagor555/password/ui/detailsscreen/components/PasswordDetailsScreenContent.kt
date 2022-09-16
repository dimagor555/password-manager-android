package ru.dimagor555.password.ui.detailsscreen.components

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
import ru.dimagor555.password.ui.detailsscreen.model.PasswordDetailsStore.Action
import ru.dimagor555.password.ui.detailsscreen.model.PasswordDetailsStore.State
import ru.dimagor555.password.ui.detailsscreen.model.PasswordState
import ru.dimagor555.password.ui.core.LargePaddingColumn
import ru.dimagor555.password.R
import ru.dimagor555.ui.core.theme.PasswordManagerTheme

@Composable
internal fun PasswordDetailsScreenContent(
    state: State,
    sendAction: (Action) -> Unit
) {
    LargePaddingColumn(modifier = Modifier.fillMaxWidth()) {
        LoginTextRow(
            login = state.passwordState.login,
            onCopyLogin = { sendAction(Action.CopyLogin) }
        )
        PasswordTextRow(
            password = state.passwordText.passwordText,
            onCopyPassword = { sendAction(Action.CopyPassword) }
        )
        TogglePasswordVisibilityButton(
            isVisible = state.passwordText.isVisible,
            onToggleVisibility = { sendAction(Action.TogglePasswordVisibility) }
        )
    }
}

@Composable
private fun LoginTextRow(
    login: String,
    onCopyLogin: () -> Unit
) {
    CopyableTextRow(
        text = login,
        headline = stringResource(R.string.login),
        buttonContentDescription = stringResource(R.string.copy_login),
        onCopy = onCopyLogin
    )
}

@Composable
private fun PasswordTextRow(
    password: String,
    onCopyPassword: () -> Unit
) {
    CopyableTextRow(
        text = password,
        headline = stringResource(R.string.password),
        buttonContentDescription = stringResource(R.string.copy_password),
        onCopy = onCopyPassword
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
                    state = State(
                        passwordState = PasswordState(login = "Username1234")
                    ),
                    sendAction = {}
                )
            }
        }
    }
}