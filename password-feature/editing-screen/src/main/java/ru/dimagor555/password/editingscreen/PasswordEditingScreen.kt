package ru.dimagor555.password.editingscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import ru.dimagor555.password.editingcore.CommonPasswordEditingScreen
import ru.dimagor555.password.editingscreen.components.SaveChangesDialog
import ru.dimagor555.password.editingscreen.model.PasswordEditingEvent
import ru.dimagor555.password.editingscreen.model.PasswordEditingEvent.OnExitScreenRequest
import ru.dimagor555.password.editingscreen.model.PasswordEditingEvent.UpdateSaveDialogVisibility

@Composable
fun PasswordEditingScreen(
    passwordId: Int,
    generatedPassword: String?,
    onNavigateToPasswordGenerationScreen: () -> Unit,
    navigateBack: () -> Unit
) {
    val viewModel: PasswordEditingViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(passwordId) {
        viewModel.sendEvent(PasswordEditingEvent.LoadPassword(passwordId))
    }

    CommonPasswordEditingScreen(
        topAppBarTitle = stringResource(R.string.edit),
        viewModel = viewModel,
        generatedPassword = generatedPassword,
        onNavigateToPasswordGenerationScreen = onNavigateToPasswordGenerationScreen,
        onNavigateBackRequest = { viewModel.sendEvent(OnExitScreenRequest) },
        navigateBack = navigateBack
    ) { onTryFinishEditing ->
        if (state.isSaveDialogVisible)
            SaveChangesDialog(
                onSave = onTryFinishEditing,
                onDiscard = navigateBack,
                onDismiss = { viewModel.sendEvent(UpdateSaveDialogVisibility(false)) }
            )
    }
}