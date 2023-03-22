package ru.dimagor555.masterpassword.ui.editscreen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import ru.dimagor555.masterpassword.ui.editscreen.model.EditMasterPasswordStore.Action
import ru.dimagor555.masterpassword.ui.editscreen.model.EditMasterPasswordStore.State
import ru.dimagor555.res.core.MR
import ru.dimagor555.ui.core.component.button.SimpleButton
import ru.dimagor555.ui.core.theme.PasswordManagerTheme
import ru.dimagor555.ui.core.util.Preview
import ru.dimagor555.ui.core.util.stringResource

@Composable
internal fun EditPrimaryPasswordScreenContent(
    state: State,
    sendAction: (Action) -> Unit,
    onGeneratePassword: () -> Unit
) {
    EditPasswordScreenTemplate(
        title = stringResource(MR.strings.primary_password_screen_title),
        isError = state.isError,
        bottomButton = {
            SimpleButton(
                text = stringResource(MR.strings.next),
                onClick = { sendAction(Action.GoNext) }
            )
        }
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            PrimaryPasswordField(state = state, sendAction = sendAction)
            SimpleButton(
                text = stringResource(MR.strings.generate),
                onClick = onGeneratePassword
            )
        }
    }
}

@Composable
private fun PrimaryPasswordField(
    state: State,
    sendAction: (Action) -> Unit
) {
    MasterPasswordInputField(
        state = state,
        sendAction = sendAction,
        placeholder = { PrimaryPasswordFieldPlaceholder() },
        imeAction = ImeAction.Next,
        onImeAction = { sendAction(Action.GoNext) }
    )
}

@Composable
private fun PrimaryPasswordFieldPlaceholder() {
    Text(
        text = stringResource(MR.strings.primary_password_field_placeholder),
        style = MaterialTheme.typography.body1
    )
}

@Preview
@Composable
private fun EditPrimaryPasswordScreenContentPreview() {
    PasswordManagerTheme {
        Surface {
            EditPrimaryPasswordScreenContent(
                state = State(),
                sendAction = {},
                onGeneratePassword = {}
            )
        }
    }
}
