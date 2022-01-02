package ru.dimagor555.password.editingcore

import androidx.activity.compose.BackHandler
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ru.dimagor555.password.editingcore.components.PasswordEditingScreenContent
import ru.dimagor555.password.editingcore.components.PasswordEditingTopAppBar
import ru.dimagor555.password.editingcore.model.PasswordEditingEvent

@Composable
fun CommonPasswordEditingScreen(
    topAppBarTitle: String,
    viewModel: CommonPasswordEditingViewModel,
    generatedPassword: String?,
    onNavigateToPasswordGenerationScreen: () -> Unit,
    onNavigateBackRequest: () -> Unit,
    navigateBack: () -> Unit,
    dialog: @Composable (onTryFinishEditing: () -> Unit) -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    val sendEvent = viewModel::sendEvent
    val onTryFinishEditing = { sendEvent(PasswordEditingEvent.TryFinishEditing) }

    LaunchedEffect(generatedPassword) {
        if (generatedPassword != null)
            sendEvent(PasswordEditingEvent.OnPasswordChanged(generatedPassword))
    }

    Scaffold(
        topBar = {
            PasswordEditingTopAppBar(
                title = topAppBarTitle,
                onTryFinishEditing = onTryFinishEditing,
                onNavigateBack = onNavigateBackRequest
            )
        }
    ) {
        PasswordEditingScreenContent(
            state = state,
            onTitleChange = { sendEvent(PasswordEditingEvent.OnTitleChanged(it)) },
            onLoginChange = { sendEvent(PasswordEditingEvent.OnLoginChanged(it)) },
            onPasswordChange = { sendEvent(PasswordEditingEvent.OnPasswordChanged(it)) },
            onTogglePasswordVisibility = { sendEvent(PasswordEditingEvent.TogglePasswordVisibility) },
            onTryFinishEditing = onTryFinishEditing,
            onGenerateClick = onNavigateToPasswordGenerationScreen
        )
        dialog(onTryFinishEditing)
    }
    BackHandler(onBack = onNavigateBackRequest)
    LaunchedEffect(key1 = state.isExitFromScreen) {
        if (state.isExitFromScreen)
            navigateBack()
    }
}