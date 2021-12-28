package ru.dimagor555.masterpassword.editscreen.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.dimagor555.masterpassword.editscreen.R
import ru.dimagor555.masterpassword.editscreen.model.EditMasterPasswordStore.Action
import ru.dimagor555.masterpassword.editscreen.model.EditMasterPasswordStore.State
import ru.dimagor555.ui.core.component.button.SimpleButton
import ru.dimagor555.ui.core.theme.PasswordManagerTheme

@Composable
internal fun EditPrimaryPasswordScreenContent(
    state: State,
    sendAction: (Action) -> Unit,
    onGeneratePassword: () -> Unit
) {
    EditPasswordScreenTemplate(
        title = stringResource(R.string.primary_password_screen_title),
        isError = state.isError,
        bottomButton = {
            SimpleButton(
                text = stringResource(R.string.next),
                onClick = { sendAction(Action.GoNext) }
            )
        }
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            PrimaryPasswordField(state = state, sendAction = sendAction)
            SimpleButton(
                text = stringResource(R.string.generate),
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
        text = stringResource(R.string.primary_password_field_placeholder),
        style = MaterialTheme.typography.body1
    )
}

@Preview("EditPrimaryPasswordScreenContent")
@Preview("EditPrimaryPasswordScreenContent (ru)", locale = "ru")
@Preview("EditPrimaryPasswordScreenContent (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
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
