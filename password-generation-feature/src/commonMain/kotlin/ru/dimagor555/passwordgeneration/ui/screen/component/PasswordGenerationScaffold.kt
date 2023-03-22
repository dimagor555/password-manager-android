package ru.dimagor555.passwordgeneration.ui.screen.component

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import ru.dimagor555.passwordgeneration.ui.screen.model.PasswordGenerationStore.Action
import ru.dimagor555.passwordgeneration.ui.screen.model.PasswordGenerationStore.State
import ru.dimagor555.ui.core.component.SingleSnackbarScaffold

@Composable
internal fun PasswordGenerationScaffold(
    state: State,
    sendAction: (Action) -> Unit,
    onNavigateBack: (String?) -> Unit,
    content: @Composable (snackbarHostState: SnackbarHostState) -> Unit
) {
    SingleSnackbarScaffold(
        topBar = {
            PasswordGenerationTopAppBar(
                onNavigateBackWithPassword = { onNavigateBack(state.password) },
                onNavigateBack = { onNavigateBack(null) }
            )
        },
        floatingActionButton = {
            GeneratePasswordFloatingActionButton(
                isEnabled = state.canGenerate,
                onGeneratePassword = { sendAction(Action.GeneratePassword) }
            )
        }
    ) { _, snackbarHostState ->
        content(snackbarHostState)
    }
}