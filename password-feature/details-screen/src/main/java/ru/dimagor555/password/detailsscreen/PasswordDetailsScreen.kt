package ru.dimagor555.password.detailsscreen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import ru.dimagor555.password.detailsscreen.components.PasswordDetailsScaffold
import ru.dimagor555.password.detailsscreen.components.PasswordDetailsScreenContent
import ru.dimagor555.password.detailsscreen.components.RemovePasswordDialog
import ru.dimagor555.password.detailsscreen.components.SideEffectHandler
import ru.dimagor555.password.detailsscreen.model.PasswordDetailsStore.Action
import ru.dimagor555.password.detailsscreen.model.PasswordDetailsStore.State
import ru.dimagor555.password.detailsscreen.model.PasswordState
import ru.dimagor555.ui.core.component.FullscreenCircularProgressBar
import ru.dimagor555.ui.core.theme.PasswordManagerTheme

@Composable
fun PasswordDetailsScreen(
    passwordId: Int,
    navigateBack: () -> Unit,
    navigateToPasswordEditingScreen: () -> Unit
) {
    val viewModel: PasswordDetailsViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(passwordId) {
        viewModel.sendAction(Action.LoadPassword(passwordId))
    }

    when (state.isLoading) {
        true -> FullscreenCircularProgressBar()
        false -> PasswordDetailsScreen(
            viewModel = viewModel,
            state = state,
            navigateBack = navigateBack,
            navigateToPasswordEditingScreen = navigateToPasswordEditingScreen
        )
    }

    LaunchedEffect(state.isExitScreen) {
        if (state.isExitScreen)
            navigateBack()
    }
}

@Composable
private fun PasswordDetailsScreen(
    viewModel: PasswordDetailsViewModel,
    state: State,
    navigateBack: () -> Unit,
    navigateToPasswordEditingScreen: () -> Unit
) {
    PasswordDetailsScaffold(
        passwordState = state.passwordState,
        sendAction = viewModel::sendAction,
        onNavigateBack = navigateBack,
        navigateToPasswordEditingScreen = navigateToPasswordEditingScreen
    ) { onShowSnackbar ->
        PasswordDetailsScreenContent(
            state = state,
            sendAction = viewModel::sendAction
        )
        RemovePasswordDialogWrapper(state = state, sendAction = viewModel::sendAction)
        SideEffectHandler(viewModel = viewModel, onShowSnackbar = onShowSnackbar)
    }
}

@Composable
private fun RemovePasswordDialogWrapper(
    state: State,
    sendAction: (Action) -> Unit
) {
    if (state.isRemoveDialogVisible)
        RemovePasswordDialog(
            onRemovePassword = { sendAction(Action.RemovePassword) },
            onDismiss = { sendAction(Action.ChangeRemoveDialogVisibility(visible = false)) }
        )
}

@Preview("Password details screen")
@Preview("Password details screen (ru)", locale = "ru")
@Preview("Password details screen (dark)", uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PasswordDetailsScreenPreview() {
    val passwordViewState = PasswordState(
        title = "Google",
        login = "username",
        isFavourite = false
    )
    PasswordManagerTheme {
        PasswordDetailsScaffold(
            passwordState = passwordViewState,
            sendAction = {},
            onNavigateBack = { },
            navigateToPasswordEditingScreen = { }
        ) {
            PasswordDetailsScreenContent(
                state = State(passwordState = passwordViewState),
                sendAction = {}
            )
        }
    }
}