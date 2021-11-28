package ru.dimagor555.passwordgenerator.screen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import ru.dimagor555.passwordgenerator.screen.components.PasswordGenerationScaffold
import ru.dimagor555.passwordgenerator.screen.components.PasswordGenerationScreenContent
import ru.dimagor555.passwordgenerator.screen.model.PasswordGenerationViewState
import ru.dimagor555.ui.core.theme.PasswordManagerTheme

@Composable
fun PasswordGenerationScreen(
    onNavigateBack: (String?) -> Unit
) {
    val viewModel: PasswordGenerationViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    PasswordGenerationScaffold(
        state = state,
        sendEvent = viewModel::sendEvent,
        onNavigateBack = onNavigateBack
    ) {
        PasswordGenerationScreenContent(state = state, sendEvent = viewModel::sendEvent)
    }
}

@Preview("Password generation screen")
@Preview("Password generation screen (ru)", locale = "ru")
@Preview("Password generation screen (dark)", uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PasswordGenerationScreenPreview() {
    PasswordManagerTheme {
        PasswordGenerationScaffold(
            state = PasswordGenerationViewState(),
            sendEvent = {},
            onNavigateBack = {}
        ) {

        }
    }
}