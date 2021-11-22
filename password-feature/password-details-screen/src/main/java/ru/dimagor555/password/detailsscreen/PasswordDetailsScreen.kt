package ru.dimagor555.password.detailsscreen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import ru.dimagor555.core.UiComponentVisibility
import ru.dimagor555.password.detailsscreen.components.PasswordDetailsScaffold
import ru.dimagor555.password.detailsscreen.components.PasswordDetailsScreenContent
import ru.dimagor555.password.detailsscreen.components.RemovePasswordDialog
import ru.dimagor555.password.detailsscreen.model.PasswordDetailsEvent
import ru.dimagor555.password.detailsscreen.model.PasswordDetailsEvent.RemovePassword
import ru.dimagor555.password.detailsscreen.model.PasswordDetailsEvent.UpdateRemoveDialogVisibility
import ru.dimagor555.password.detailsscreen.model.PasswordDetailsViewState
import ru.dimagor555.password.detailsscreen.model.PasswordViewState
import ru.dimagor555.ui.core.theme.PasswordManagerTheme

@Composable
fun PasswordDetailsScreen(
    navigateBack: () -> Unit,
    navigateToPasswordEditingScreen: () -> Unit
) {
    val viewModel: PasswordDetailsViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    PasswordDetailsScaffold(
        passwordState = state.passwordViewState,
        sendEvent = viewModel::sendEvent,
        onNavigateBack = navigateBack,
        navigateToPasswordEditingScreen = navigateToPasswordEditingScreen
    ) { onShowSnackbar ->
        PasswordDetailsScreenContent(
            state = state,
            sendEvent = viewModel::sendEvent,
            showSnackbar = onShowSnackbar
        )
        RemovePasswordDialogWrapper(state = state, sendEvent = viewModel::sendEvent)
    }
    LaunchedEffect(key1 = state.isPasswordRemoved) {
        if (state.isPasswordRemoved)
            navigateBack()
    }
}

@Composable
private fun RemovePasswordDialogWrapper(
    state: PasswordDetailsViewState,
    sendEvent: (PasswordDetailsEvent) -> Unit
) {
    if (state.removeDialogVisibility == UiComponentVisibility.Show)
        RemovePasswordDialog(
            onRemovePassword = { sendEvent(RemovePassword) },
            onDismiss = { sendEvent(UpdateRemoveDialogVisibility(UiComponentVisibility.Hide)) }
        )
}

@Preview("Password details screen")
@Preview("Password details screen (ru)", locale = "ru")
@Preview("Password details screen (dark)", uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PasswordDetailsScreenPreview() {
    val passwordViewState = PasswordViewState(
        title = "Google",
        login = "username",
        isFavourite = false
    )
    PasswordManagerTheme {
        PasswordDetailsScaffold(
            passwordState = passwordViewState,
            sendEvent = {},
            onNavigateBack = { },
            navigateToPasswordEditingScreen = { }
        ) {
            PasswordDetailsScreenContent(
                state = PasswordDetailsViewState(passwordViewState = passwordViewState),
                sendEvent = {},
                showSnackbar = it
            )
        }
    }
}