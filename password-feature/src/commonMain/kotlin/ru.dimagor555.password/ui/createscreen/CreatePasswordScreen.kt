package ru.dimagor555.password.ui.createscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ru.dimagor555.res.core.MR
import ru.dimagor555.password.ui.commoneditscreen.CommonEditPasswordScreen
import ru.dimagor555.ui.core.util.stringResource

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
        topAppBarTitle = stringResource(MR.strings.create),
        onNavigateToPasswordGenerationScreen = onNavigateToPasswordGenerationScreen,
        onNavigateBack = onNavigateBack
    )
    LaunchedEffect(state.isExitScreen) {
        if (state.isExitScreen)
            onNavigateBack()
    }
}