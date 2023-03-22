package ru.dimagor555.masterpassword.ui.editscreen.component

import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.ImeAction
import ru.dimagor555.masterpassword.ui.editscreen.model.EditMasterPasswordStore.Action
import ru.dimagor555.masterpassword.ui.editscreen.model.EditMasterPasswordStore.State
import ru.dimagor555.res.core.MR
import ru.dimagor555.ui.core.theme.PasswordManagerTheme
import ru.dimagor555.ui.core.util.Preview
import ru.dimagor555.ui.core.util.stringResource

@Composable
internal fun EditConfirmPasswordScreenContent(
    state: State,
    sendAction: (Action) -> Unit
) {
    EditPasswordScreenTemplate(
        title = stringResource(MR.strings.confirm_password_screen_title),
        isError = state.isError,
        bottomButton = {
            Button(
                onClick = { sendAction(Action.GoNext) },
                enabled = !state.isSavingStarted
            ) {
                Text(text = stringResource(MR.strings.ready))
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
        text = stringResource(MR.strings.confirm_password_field_placeholder),
        style = MaterialTheme.typography.body1
    )
}

@Preview
@Composable
private fun EditConfirmPasswordScreenContentPreview() {
    PasswordManagerTheme {
        Surface {
            EditConfirmPasswordScreenContent(state = State(), sendAction = {})
        }
    }
}
