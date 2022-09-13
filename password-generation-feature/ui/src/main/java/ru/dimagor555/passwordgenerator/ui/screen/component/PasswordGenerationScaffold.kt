package ru.dimagor555.passwordgenerator.ui.screen.component

import androidx.compose.runtime.Composable
import ru.dimagor555.passwordgenerator.ui.screen.model.PasswordGenerationStore.Action
import ru.dimagor555.passwordgenerator.ui.screen.model.PasswordGenerationStore.State
import ru.dimagor555.ui.core.component.SingleSnackbarScaffold
import ru.dimagor555.ui.core.util.SnackbarMessage

@Composable
internal fun PasswordGenerationScaffold(
    state: State,
    sendAction: (Action) -> Unit,
    onNavigateBack: (String?) -> Unit,
    content: @Composable ((SnackbarMessage) -> Unit) -> Unit
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
    ) { _, onShowSnackbar ->
        content(onShowSnackbar)
    }
}