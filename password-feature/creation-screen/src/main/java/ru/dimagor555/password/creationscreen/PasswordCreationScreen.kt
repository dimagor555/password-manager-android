package ru.dimagor555.password.creationscreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import ru.dimagor555.password.editingcore.CommonPasswordEditingScreen
import ru.dimagor555.password.ui.core.R

@Composable
fun PasswordCreationScreen(
    onGeneratePassword: () -> String?,
    navigateBack: () -> Unit
) {
    val viewModel: PasswordCreationViewModel = hiltViewModel()
    CommonPasswordEditingScreen(
        topAppBarTitle = stringResource(R.string.create),
        viewModel = viewModel,
        onGeneratePassword = onGeneratePassword,
        onNavigateBackRequest = navigateBack,
        navigateBack = navigateBack
    )
}