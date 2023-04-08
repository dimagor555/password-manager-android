package ru.dimagor555.masterpassword.ui.editscreen.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import ru.dimagor555.masterpassword.ui.editscreen.model.EditMasterPasswordStore
import ru.dimagor555.ui.core.component.textfield.PasswordInputField
import ru.dimagor555.ui.core.util.bringIntoViewOnFocus
import ru.dimagor555.ui.core.util.resolve

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun MasterPasswordInputField(
    state: EditMasterPasswordStore.State,
    sendAction: (EditMasterPasswordStore.Action) -> Unit,
    imeAction: ImeAction,
    onImeAction: () -> Unit,
    modifier: Modifier = Modifier,
    placeholder: (@Composable () -> Unit)? = null
) {
    PasswordInputField(
        value = state.currPassword.text,
        onValueChange = { sendAction(EditMasterPasswordStore.Action.ChangePassword(it)) },
        isPasswordVisible = state.currPassword.isVisible,
        onTogglePasswordVisibility = { sendAction(EditMasterPasswordStore.Action.TogglePasswordVisibility) },
        error = state.currPassword.error?.resolve(),
        placeholder = placeholder,
        modifier = modifier
            .fillMaxWidth()
            .bringIntoViewOnFocus(),
        imeAction = imeAction,
        keyboardActions = KeyboardActions { onImeAction() }
    )
}