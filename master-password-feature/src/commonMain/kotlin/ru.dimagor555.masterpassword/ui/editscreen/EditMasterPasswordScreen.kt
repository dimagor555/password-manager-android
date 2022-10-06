package ru.dimagor555.masterpassword.ui.editscreen

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ru.dimagor555.masterpassword.ui.editscreen.component.EditConfirmPasswordScreenContent
import ru.dimagor555.masterpassword.ui.editscreen.component.EditPrimaryPasswordScreenContent
import ru.dimagor555.masterpassword.ui.editscreen.model.EditMasterPasswordStore.Action
import ru.dimagor555.masterpassword.ui.editscreen.model.EditMasterPasswordStore.State

@Composable
fun EditMasterPasswordScreen(component: EditMasterPassword) {
    component as EditMasterPasswordComponent

    val state by component.state.collectAsState()

    LaunchedEffect(component.generatedPassword) {
        if (component.generatedPassword != null)
            component.sendAction(Action.ChangePassword(component.generatedPassword))
    }

    Surface {
        when (state.stage) {
            State.Stage.Primary -> {
                EditPrimaryPasswordScreenContent(
                    state = state,
                    sendAction = component::sendAction,
                    onGeneratePassword = { component.navigateToPasswordGenerationScreen() }
                )
            }
            State.Stage.Confirm -> {
                EditConfirmPasswordScreenContent(
                    state = state,
                    sendAction = component::sendAction
                )
            }
        }
    }
    LaunchedEffect(state.exitScreenState) {
        val exitScreenState = state.exitScreenState
        if (exitScreenState !is State.ExitScreenState.Exit)
            return@LaunchedEffect
        when (exitScreenState.success) {
            true -> component.navigateToOverviewScreen()
            false -> component.navigateBack()
        }
    }
}