package ru.dimagor555.password.ui.createscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import org.koin.androidx.compose.koinViewModel
import ru.dimagor555.password.ui.R
import ru.dimagor555.password.ui.commoneditscreen.CommonEditPasswordScreen

@Composable
fun CreatePasswordScreen(
    generatedPassword: String?,
    onNavigateToPasswordGenerationScreen: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val viewModel = koinViewModel<CreatePasswordViewModel>()
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