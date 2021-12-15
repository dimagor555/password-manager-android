package ru.dimagor555.passwordgenerator.screen.component

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import ru.dimagor555.passwordgenerator.screen.model.PasswordGenerationEvent
import ru.dimagor555.passwordgenerator.screen.model.PasswordGenerationViewState

@Composable
internal fun PasswordGenerationScaffold(
    state: PasswordGenerationViewState,
    sendEvent: (PasswordGenerationEvent) -> Unit,
    onNavigateBack: (String?) -> Unit,
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            PasswordGenerationTopAppBar(
                onNavigateBackWithPassword = { onNavigateBack(state.generatedPassword) },
                onNavigateBack = { onNavigateBack(null) }
            )
        },
        floatingActionButton = {
            GeneratePasswordFloatingActionButton(
                isEnabled = state.isGenerateButtonEnabled,
                onGeneratePassword = { sendEvent(PasswordGenerationEvent.GeneratePassword) }
            )
        }
    ) {
        content()
    }
}