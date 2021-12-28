package ru.dimagor555.masterpassword.editscreen.component

import android.content.res.Configuration
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import ru.dimagor555.masterpassword.editscreen.R
import ru.dimagor555.masterpassword.editscreen.model.EditMasterPasswordStore.Action
import ru.dimagor555.masterpassword.editscreen.model.EditMasterPasswordStore.State
import ru.dimagor555.ui.core.theme.PasswordManagerTheme

@Composable
internal fun EditConfirmPasswordScreenContent(
    state: State,
    sendAction: (Action) -> Unit
) {
    EditPasswordScreenTemplate(
        title = stringResource(R.string.confirm_password_screen_title),
        isError = state.isError,
        bottomButton = {
            Button(
                onClick = { sendAction(Action.GoNext) },
                enabled = !state.isSavingStarted
            ) {
                Text(text = stringResource(R.string.ready))
            }
        }
    ) {
        ConfirmPasswordField(state = state, sendAction = sendAction)
    }
}

@Composable
private fun ConfirmPasswordField(
    state: State,
    sendAction: (Action) -> Unit
) {
    MasterPasswordInputField(
        state = state,
        sendAction = sendAction,
        imeAction = ImeAction.Done,
        onImeAction = { sendAction(Action.GoNext) },
        placeholder = { ConfirmPasswordFieldPlaceholder() }
    )
}

@Composable
private fun ConfirmPasswordFieldPlaceholder() {
    Text(
        text = stringResource(R.string.confirm_password_field_placeholder),
        style = MaterialTheme.typography.body1
    )
}

@Preview("EditConfirmPasswordScreenContent")
@Preview("EditConfirmPasswordScreenContent (ru)", locale = "ru")
@Preview("EditConfirmPasswordScreenContent (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun EditConfirmPasswordScreenContentPreview() {
    PasswordManagerTheme {
        Surface {
            EditConfirmPasswordScreenContent(state = State(), sendAction = {})
        }
    }
}
