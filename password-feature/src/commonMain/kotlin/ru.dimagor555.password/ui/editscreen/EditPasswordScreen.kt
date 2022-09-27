package ru.dimagor555.password.ui.editscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ru.dimagor555.res.core.MR
import ru.dimagor555.password.ui.commoneditscreen.CommonEditPasswordScreen
import ru.dimagor555.password.ui.commoneditscreen.model.CommonEditPasswordStore
import ru.dimagor555.password.ui.editscreen.component.SaveChangesDialog
import ru.dimagor555.password.ui.editscreen.model.EditPasswordStore.Action
import ru.dimagor555.password.ui.editscreen.model.EditPasswordStore.State
import ru.dimagor555.ui.core.util.stringResource

@Composable
fun EditPasswordScreen(
    passwordId: Int,
    generatedPassword: String?,
    onNavigateToPasswordGenerationScreen: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val viewModel = koinViewModel<EditPasswordViewModel>()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(passwordId) {
        viewModel.sendAction(Action.LoadPassword(passwordId))
    }

    CommonEditPasswordScreen(
        store = viewModel.commonEditPasswordStore,
        generatedPassword = generatedPassword,
        topAppBarTitle = stringResource(MR.strings.edit),
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