package ru.dimagor555.masterpassword.ui.editscreen.composable

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import ru.dimagor555.masterpassword.ui.editscreen.store.EditMasterPasswordStore.Action
import ru.dimagor555.masterpassword.ui.editscreen.store.EditMasterPasswordStore.State

@Composable
internal fun EditMasterPasswordScreenContent(
    state: State,
    sendAction: (Action) -> Unit,
) {
    Surface {
        when (state.stage) {
            State.Stage.Primary -> {
                EditPrimaryPasswordScreenContent(
                    state = state,
                    sendAction = sendAction,
                    onGeneratePassword = {},
                )
            }
            State.Stage.Confirm -> {
                EditConfirmPasswordScreenContent(
                    state = state,
                    sendAction = sendAction,
                )
            }
        }
    }
}