package ru.dimagor555.password.editingscreen

import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import ru.dimagor555.core.UiComponentVisibility
import ru.dimagor555.password.editingcore.CommonPasswordEditingScreen
import ru.dimagor555.password.editingscreen.components.SaveChangesDialog
import ru.dimagor555.password.editingscreen.model.PasswordEditingEvent.*

@Composable
fun PasswordEditingScreen(
    onGeneratePassword: () -> String?,
    navigateBack: () -> Unit
) {
    val viewModel: PasswordEditingViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    CommonPasswordEditingScreen(
        topAppBarTitle = stringResource(R.string.edit),
        viewModel = viewModel,
        onGeneratePassword = onGeneratePassword,
        onNavigateBackRequest = { viewModel.sendEvent(OnExitScreenRequest) },
        navigateBack = navigateBack
    ) { onTryFinishEditing ->
        if (state.saveDialogVisibility == UiComponentVisibility.Hide)
            return@CommonPasswordEditingScreen
        SaveChangesDialog(
            onSave = onTryFinishEditing,
            onDiscard = navigateBack,
            onDismiss = {
                viewModel.sendEvent(UpdateSaveDialogVisibility(UiComponentVisibility.Hide))
            }
        )
    }
}