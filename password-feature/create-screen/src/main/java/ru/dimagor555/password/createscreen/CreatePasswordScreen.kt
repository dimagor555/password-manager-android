package ru.dimagor555.password.createscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import ru.dimagor555.password.commonedit.CommonEditPasswordScreen
import ru.dimagor555.password.ui.core.R

@Composable
fun CreatePasswordScreen(
    generatedPassword: String?,
    onNavigateToPasswordGenerationScreen: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val viewModel: CreatePasswordViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    CommonEditPasswordScreen(
        store = viewModel.commonEditPasswordStore,
        generatedPassword = generatedPassword,
        topAppBarTitle = stringResource(R.string.create),
        onNavigateToPasswordGenerationScreen = onNavigateToPasswordGenerationScreen,
        onNavigateBack = onNavigateBack
    )
    LaunchedEffect(state.isExitScreen) {
        if (state.isExitScreen)
            onNavigateBack()
    }
}