package ru.dimagor555.password.editscreen

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import ru.dimagor555.password.commonedit.CommonEditPasswordScreen
import ru.dimagor555.password.commonedit.model.CommonEditPasswordStore
import ru.dimagor555.password.editscreen.component.SaveChangesDialog
import ru.dimagor555.password.editscreen.model.EditPasswordStore.Action
import ru.dimagor555.password.editscreen.model.EditPasswordStore.State

@Composable
fun EditPasswordScreen(
    passwordId: Int,
    generatedPassword: String?,
    onNavigateToPasswordGenerationScreen: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val viewModel: EditPasswordViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(passwordId) {
        viewModel.sendAction(Action.LoadPassword(passwordId))
    }

    CommonEditPasswordScreen(
        store = viewModel.commonEditPasswordStore,
        generatedPassword = generatedPassword,
        topAppBarTitle = stringResource(R.string.edit),
        onNavigateToPasswordGenerationScreen = onNavigateToPasswordGenerationScreen,
        onNavigateBack = { viewModel.sendAction(Action.RequestExitScreen) }
    )
    SaveChangesDialogWrapper(
        state = state,
        sendAction = viewModel::sendAction,
        commonEditPasswordStore = viewModel.commonEditPasswordStore
    )

    BackHandler { viewModel.sendAction(Action.RequestExitScreen) }
    LaunchedEffect(state.exitScreenState) {
        if (state.isExitScreen)
            onNavigateBack()
    }
}

@Composable
private fun SaveChangesDialogWrapper(
    state: State,
    sendAction: (Action) -> Unit,
    commonEditPasswordStore: CommonEditPasswordStore
) {
    if (state.isSaveDialogVisible)
        SaveChangesDialog(
            onSave = {
                commonEditPasswordStore.sendAction(
                    CommonEditPasswordStore.Action.Validate
                )
            },
            onDiscard = { sendAction(Action.ForceExitScreen) },
            onDismiss = { sendAction(Action.DismissExitScreenRequest) }
        )
}