package ru.dimagor555.password.ui.detailsscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ru.dimagor555.password.domain.password.field.*
import ru.dimagor555.password.ui.detailsscreen.components.PasswordDetailsScaffold
import ru.dimagor555.password.ui.detailsscreen.components.PasswordDetailsScreenContent
import ru.dimagor555.password.ui.detailsscreen.components.RemovePasswordDialog
import ru.dimagor555.password.ui.detailsscreen.model.PasswordDetailsStore
import ru.dimagor555.password.ui.detailsscreen.model.PasswordDetailsStore.Action
import ru.dimagor555.password.ui.detailsscreen.model.PasswordDetailsStore.State
import ru.dimagor555.password.ui.detailsscreen.model.PasswordState
import ru.dimagor555.ui.core.component.FullscreenCircularProgressBar
import ru.dimagor555.ui.core.theme.PasswordManagerTheme
import ru.dimagor555.ui.core.util.OnSideEffect
import ru.dimagor555.ui.core.util.Preview
import ru.dimagor555.ui.core.util.createLongSnackbarMessage

@Composable
fun PasswordDetailsScreen(component: PasswordDetailsComponent) {
    component as PasswordDetailsComponentImpl

    val state by component.state.collectAsState()

    LaunchedEffect(component.passwordId, component.parentId) {
        component.sendAction(Action.LoadPassword(component.passwordId, component.parentId))
    }

    when (state.isLoading) {
        true -> FullscreenCircularProgressBar()
        false -> PasswordDetailsScreen(
            component = component,
            state = state,
            navigateBack = component.callbacks.navigateBack,
            navigateToPasswordEditingScreen = {
                component.callbacks.navigateToPasswordEditingScreen(state.passwordId)
            },
        )
    }

    LaunchedEffect(state.isExitScreen) {
        if (state.isExitScreen)
            component.callbacks.navigateBack()
    }
}

@Composable
private fun PasswordDetailsScreen(
    component: PasswordDetailsComponentImpl,
    state: State,
    navigateBack: () -> Unit,
    navigateToPasswordEditingScreen: () -> Unit,
) {
    PasswordDetailsScaffold(
        passwordState = state.passwordState,
        sendAction = component::sendAction,
        onNavigateBack = navigateBack,
        navigateToPasswordEditingScreen = navigateToPasswordEditingScreen,
    ) { snackbarHostState ->
        PasswordDetailsScreenContent(
            state = state,
            sendAction = component::sendAction,
        )
        RemovePasswordDialogWrapper(state = state, sendAction = component::sendAction)
        OnSideEffect(
            component = component,
            snackbarHostState = snackbarHostState,
            onSideEffect = { sideEffect, showSnackbar ->
                when (sideEffect) {
                    is PasswordDetailsStore.SideEffect.ShowMessage -> showSnackbar(
                        createLongSnackbarMessage(sideEffect.message)
                    )
                }
            },
        )
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
            onDismiss = { sendAction(Action.ChangeRemoveDialogVisibility(visible = false)) },
        )
}

@Preview
@Composable
private fun PasswordDetailsScreenPreview() {
    val passwordViewState = PasswordState(
        fields = mapOf(
            SITE_FIELD_KEY to "",
            TITLE_FIELD_KEY to "",
            LOGIN_FIELD_KEY to "",
            PHONE_FIELD_KEY to "",
            PASSWORD_FIELD_KEY to "",
        ),
        isPasswordVisible = true,
        isFavourite = false,
    )
    PasswordManagerTheme {
        PasswordDetailsScaffold(
            passwordState = passwordViewState,
            sendAction = {},
            onNavigateBack = { },
            navigateToPasswordEditingScreen = { },
        ) {
            PasswordDetailsScreenContent(
                state = State(passwordState = passwordViewState),
                sendAction = {},
            )
        }
    }
}