package ru.dimagor555.masterpassword.ui.editscreen

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ModalBottomSheetValue.Expanded
import androidx.compose.material.ModalBottomSheetValue.Hidden
import androidx.compose.runtime.*
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import ru.dimagor555.masterpassword.ui.biometric.BiometricSetUpBottomSheetContent
import ru.dimagor555.masterpassword.ui.biometric.BiometricSetUpDialog
import ru.dimagor555.masterpassword.ui.editscreen.composable.EditMasterPasswordScreenContent
import ru.dimagor555.masterpassword.ui.editscreen.store.EditMasterPasswordStore.Action
import ru.dimagor555.masterpassword.ui.editscreen.store.EditMasterPasswordStore.State

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EditMasterPasswordScreen(component: EditMasterPasswordComponent) {
    component as EditMasterPasswordComponentImpl

    val state by component.state.collectAsState()

    BiometricSetUpBottomSheetLayout(
        sendAction = component::sendAction,
    ) { sheetState ->
        EditMasterPasswordScreenContent(
            state = state,
            sendAction = component::sendAction,
        )
        OnExitScreen(
            state = state,
            sheetState = sheetState,
            component = component,
        )
    }
    BiometricSetUpDialog(
        visible = state.isBiometricSetUpDialogVisible,
        onSuccess = { component.sendAction(Action.OnFinishSettingUpBiometric) },
        onDismiss = { component.sendAction(Action.HideBiometricSetUpDialog) },
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun BiometricSetUpBottomSheetLayout(
    sendAction: (Action) -> Unit,
    content: @Composable (ModalBottomSheetState) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(
        initialValue = Hidden,
        skipHalfExpanded = true,
    )
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            BiometricSetUpBottomSheetContent(
                onEnable = { sendAction(Action.ShowBiometricSetUpDialog) },
                onCancel = { sendAction(Action.OnFinishSettingUpBiometric) },
            )
        },
        sheetShape = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp,
        ),
        content = { content(sheetState) },
    )
    OnHideBottomSheet(sheetState = sheetState) {
        sendAction(Action.OnFinishSettingUpBiometric)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun OnHideBottomSheet(
    sheetState: ModalBottomSheetState,
    onHideBottomSheet: () -> Unit,
) {
    LaunchedEffect(Unit) {
        var lastSheetValue = sheetState.currentValue
        snapshotFlow { sheetState.currentValue }
            .collect { sheetValue ->
                if (sheetValue == Hidden && lastSheetValue == Expanded) {
                    onHideBottomSheet()
                }
                lastSheetValue = sheetValue
            }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun OnExitScreen(
    state: State,
    sheetState: ModalBottomSheetState,
    component: EditMasterPasswordComponentImpl,
) {
    val focusManager = LocalFocusManager.current
    val currentTask = state.currentTask
    LaunchedEffect(currentTask) {
        sheetState.showOrHide(
            state = state,
            focusManager = focusManager,
        )
        exitScreenIfShould(
            currentTask = currentTask,
            component = component,
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
private suspend fun ModalBottomSheetState.showOrHide(
    state: State,
    focusManager: FocusManager,
) {
    if (state.isSettingUpBiometricInProgress) {
        focusManager.clearFocus(force = true)
        this.show()
    } else {
        this.hide()
    }
}

private fun exitScreenIfShould(
    currentTask: State.Task,
    component: EditMasterPasswordComponentImpl,
) {
    if (currentTask !is State.Task.ExitingScreen) {
        return
    }
    when (currentTask.isSuccess) {
        true -> component.callbacks.onSuccess()
        false -> component.callbacks.onCancel()
    }
}