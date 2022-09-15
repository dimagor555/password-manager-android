package ru.dimagor555.masterpassword.ui.editscreen

import androidx.activity.compose.BackHandler
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel
import ru.dimagor555.masterpassword.ui.editscreen.component.EditConfirmPasswordScreenContent
import ru.dimagor555.masterpassword.ui.editscreen.component.EditPrimaryPasswordScreenContent
import ru.dimagor555.masterpassword.ui.editscreen.model.EditMasterPasswordStore.Action
import ru.dimagor555.masterpassword.ui.editscreen.model.EditMasterPasswordStore.State

@Composable
fun EditMasterPasswordScreen(
    generatedPassword: String?,
    onSuccess: () -> Unit,
    onCancel: () -> Unit,
    onNavigateToPasswordGenerationScreen: () -> Unit
) {
    val viewModel = koinViewModel<EditMasterPasswordViewModel>()
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