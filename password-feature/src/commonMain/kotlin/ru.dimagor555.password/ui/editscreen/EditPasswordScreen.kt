package ru.dimagor555.password.ui.editscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ru.dimagor555.password.domain.password.encryptedPassword
import ru.dimagor555.password.ui.commoneditscreen.CommonEditPasswordScreen
import ru.dimagor555.password.ui.commoneditscreen.model.CommonEditPasswordStore
import ru.dimagor555.password.ui.editscreen.component.SaveChangesDialog
import ru.dimagor555.password.ui.editscreen.model.EditPasswordStore.Action
import ru.dimagor555.password.ui.editscreen.model.EditPasswordStore.State
import ru.dimagor555.res.core.MR
import ru.dimagor555.ui.core.util.OnStoreSideEffect
import ru.dimagor555.ui.core.util.createLongSnackbarMessage
import ru.dimagor555.ui.core.util.stringResource

@Composable
fun EditPasswordScreen(component: EditPasswordComponent) {
    component as EditPasswordComponentImpl

    val state by component.editPasswordState.collectAsState()

    LaunchedEffect(component.passwordId) {
        component.sendAction(Action.LoadPassword(component.passwordId))
    }

    CommonEditPasswordScreen(
        store = component.commonEditPasswordStore,
        generatedPassword = component.commonEditPasswordState.value.passwordFields.encryptedPassword.text,
        topAppBarTitle = stringResource(MR.strings.edit),
        onNavigateToPasswordGenerationScreen = component.callbacks.onNavigateToPasswordGenerationScreen,
        onNavigateBack = { component.sendAction(Action.RequestExitScreen) },
    ) { snackbarHostState ->
        OnStoreSideEffect(
            store = component.commonEditPasswordStore,
            snackbarHostState = snackbarHostState,
            onSideEffect = { sideEffect, showSnackbar ->
                when (sideEffect) {
                    is CommonEditPasswordStore.SideEffect.ShowUnknownUpdateError -> showSnackbar(
                        createLongSnackbarMessage(sideEffect.message)
                    )
                    else -> {}
                }
            }
        )
    }
    SaveChangesDialogWrapper(
        state = state,
        sendAction = component::sendAction,
        commonEditPasswordStore = component.commonEditPasswordStore,
    )
    LaunchedEffect(state.exitScreenState) {
        if (state.isExitScreen)
            component.callbacks.onNavigateBack()
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
            onDismiss = { sendAction(Action.DismissExitScreenRequest) },
        )
}
