package ru.dimagor555.passwordgeneration.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel
import ru.dimagor555.passwordgeneration.ui.screen.component.PasswordGenerationScaffold
import ru.dimagor555.passwordgeneration.ui.screen.component.PasswordGenerationScreenContent
import ru.dimagor555.passwordgeneration.ui.screen.component.SideEffectHandler
import ru.dimagor555.passwordgeneration.ui.screen.model.PasswordGenerationStore.State
import ru.dimagor555.ui.core.theme.PasswordManagerTheme
import ru.dimagor555.ui.core.util.Preview

@Composable
fun PasswordGenerationScreen(
    onNavigateBack: (generatedPassword: String?) -> Unit
) {
    val viewModel = koinViewModel<PasswordGenerationViewModel>()
    val state by viewModel.state.collectAsState()

    PasswordGenerationScaffold(
        state = state,
        sendAction = viewModel::sendAction,
        onNavigateBack = onNavigateBack
    ) { onShowSnackbar ->
        PasswordGenerationScreenContent(state = state, sendAction = viewModel::sendAction)
        SideEffectHandler(viewModel = viewModel, onShowSnackbar = onShowSnackbar)
    }
}

@Preview
@Composable
private fun PasswordGenerationScreenPreview() {
    PasswordManagerTheme {
        PasswordGenerationScaffold(
            state = State(),
            sendAction = {},
            onNavigateBack = {}
        ) {}
    }
}