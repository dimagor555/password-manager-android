package ru.dimagor555.passwordgenerator.ui.screen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import org.koin.androidx.compose.koinViewModel
import ru.dimagor555.passwordgenerator.ui.screen.component.PasswordGenerationScaffold
import ru.dimagor555.passwordgenerator.ui.screen.component.PasswordGenerationScreenContent
import ru.dimagor555.passwordgenerator.ui.screen.component.SideEffectHandler
import ru.dimagor555.passwordgenerator.ui.screen.model.PasswordGenerationStore.State
import ru.dimagor555.ui.core.theme.PasswordManagerTheme

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

@Preview("Password generation screen")
@Preview("Password generation screen (ru)", locale = "ru")
@Preview("Password generation screen (dark)", uiMode = UI_MODE_NIGHT_YES)
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