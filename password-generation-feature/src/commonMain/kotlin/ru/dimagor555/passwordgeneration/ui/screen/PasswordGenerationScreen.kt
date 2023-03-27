package ru.dimagor555.passwordgeneration.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ru.dimagor555.passwordgeneration.ui.screen.component.PasswordGenerationScaffold
import ru.dimagor555.passwordgeneration.ui.screen.component.PasswordGenerationScreenContent
import ru.dimagor555.passwordgeneration.ui.screen.model.PasswordGenerationStore
import ru.dimagor555.passwordgeneration.ui.screen.model.PasswordGenerationStore.State
import ru.dimagor555.ui.core.theme.PasswordManagerTheme
import ru.dimagor555.ui.core.util.OnSideEffect
import androidx.compose.desktop.ui.tooling.preview.Preview
import ru.dimagor555.ui.core.util.createLongSnackbarMessage

@Composable
fun PasswordGenerationScreen(component: PasswordGenerationComponent) {
    component as PasswordGenerationComponentImpl

    val state by component.state.collectAsState()

    PasswordGenerationScaffold(
        state = state,
        sendAction = component::sendAction,
        onNavigateBack = { result ->
            component.navigateBack(result)
        }
    ) { snackbarHostState ->
        PasswordGenerationScreenContent(state = state, sendAction = component::sendAction)
        OnSideEffect(
            component = component,
            snackbarHostState = snackbarHostState,
            onSideEffect = { sideEffect, showSnackbar ->
                when (sideEffect) {
                    is PasswordGenerationStore.SideEffect.ShowMessage ->
                        showSnackbar(createLongSnackbarMessage(sideEffect.message))
                }
            }
        )
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