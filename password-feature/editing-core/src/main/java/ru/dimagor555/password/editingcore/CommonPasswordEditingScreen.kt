package ru.dimagor555.password.editingcore

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
    onGeneratePassword: () -> String?,
    onNavigateBackRequest: () -> Unit,
    navigateBack: () -> Unit,
    dialog: @Composable (onFinishEditing: () -> Unit) -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    val sendEvent = viewModel::sendEvent
    val onFinishEditing = { sendEvent(PasswordEditingEvent.TryFinishEditing) }

    Scaffold(
        topBar = {
            PasswordEditingTopAppBar(
                title = topAppBarTitle,
                onFinishEditing = onFinishEditing,
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
            onFinishEditing = onFinishEditing,
            onGenerateClick = {
                val generatedPassword = onGeneratePassword()
                generatedPassword?.let {
                    sendEvent(PasswordEditingEvent.OnPasswordChanged(it))
                }
            }
        )
        dialog(onFinishEditing)
    }
    LaunchedEffect(key1 = state.isEditingFinished) {
        if (state.isEditingFinished)
            navigateBack()
    }
}