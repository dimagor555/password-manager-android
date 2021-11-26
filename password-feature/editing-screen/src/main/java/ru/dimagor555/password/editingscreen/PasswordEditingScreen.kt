package ru.dimagor555.password.editingscreen

import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import ru.dimagor555.password.editingcore.CommonPasswordEditingScreen
import ru.dimagor555.password.editingscreen.components.SaveChangesDialog

@Composable
fun PasswordEditingScreen(
    onGeneratePassword: () -> String?,
    navigateBack: () -> Unit
) {
    val viewModel: PasswordEditingViewModel = hiltViewModel()
    var showSaveDialog by remember { mutableStateOf(false) }
    CommonPasswordEditingScreen(
        topAppBarTitle = stringResource(R.string.edit),
        viewModel = viewModel,
        onGeneratePassword = onGeneratePassword,
        onNavigateBackRequest = { showSaveDialog = true },
        navigateBack = navigateBack
    ) { onFinishEditing ->
        if (!showSaveDialog)
            return@CommonPasswordEditingScreen
        SaveChangesDialog(
            onSave = onFinishEditing,
            onDiscard = {
                showSaveDialog = false
                navigateBack()
            },
            onDismiss = { showSaveDialog = false }
        )
    }
}