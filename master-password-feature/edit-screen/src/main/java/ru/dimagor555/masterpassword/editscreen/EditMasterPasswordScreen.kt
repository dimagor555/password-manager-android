package ru.dimagor555.masterpassword.editscreen

import androidx.activity.compose.BackHandler
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import ru.dimagor555.masterpassword.editscreen.component.EditConfirmPasswordScreenContent
import ru.dimagor555.masterpassword.editscreen.component.EditPrimaryPasswordScreenContent
import ru.dimagor555.masterpassword.editscreen.model.EditMasterPasswordStore.Action
import ru.dimagor555.masterpassword.editscreen.model.EditMasterPasswordStore.State

@Composable
fun EditMasterPasswordScreen(
    generatedPassword: String?,
    onSuccess: () -> Unit,
    onCancel: () -> Unit,
    onNavigateToPasswordGenerationScreen: () -> Unit
) {
    val viewModel: EditMasterPasswordViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(generatedPassword) {
        if (generatedPassword != null)
            viewModel.sendAction(Action.ChangePassword(generatedPassword))
    }

    Surface {
        when (state.stage) {
            State.Stage.Primary -> {
                EditPrimaryPasswordScreenContent(
                    state = state,
                    sendAction = viewModel::sendAction,
                    onGeneratePassword = onNavigateToPasswordGenerationScreen
                )
            }
            State.Stage.Confirm -> {
                EditConfirmPasswordScreenContent(
                    state = state,
                    sendAction = viewModel::sendAction
                )
            }
        }
    }
    BackHandler { viewModel.sendAction(Action.GoBack) }
    LaunchedEffect(state.exitScreenState) {
        val exitScreenState = state.exitScreenState
        if (exitScreenState !is State.ExitScreenState.Exit)
            return@LaunchedEffect
        when (exitScreenState.success) {
            true -> onSuccess()
            false -> onCancel()
        }
    }
}