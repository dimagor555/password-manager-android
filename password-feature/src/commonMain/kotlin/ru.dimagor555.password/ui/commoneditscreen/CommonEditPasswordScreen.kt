package ru.dimagor555.password.ui.commoneditscreen

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ru.dimagor555.password.ui.commoneditscreen.component.CommonEditPasswordScreenContent
import ru.dimagor555.password.ui.commoneditscreen.component.CommonEditPasswordTopAppBar
import ru.dimagor555.password.ui.commoneditscreen.model.CommonEditPasswordStore
import ru.dimagor555.password.ui.commoneditscreen.model.CommonEditPasswordStore.Action

@Composable
fun CommonEditPasswordScreen(
    store: CommonEditPasswordStore,
    generatedPassword: String?,
    topAppBarTitle: String,
    onNavigateToPasswordGenerationScreen: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val state by store.state.collectAsState()

    LaunchedEffect(generatedPassword) {
        if (generatedPassword != null)
            store.sendAction(Action.ChangePassword(generatedPassword))
    }

    Scaffold(
        topBar = {
            CommonEditPasswordTopAppBar(
                title = topAppBarTitle,
                onValidate = { store.sendAction(Action.Validate) },
                onNavigateBack = onNavigateBack
            )
        }
    ) {
        CommonEditPasswordScreenContent(
            state = state,
            onTitleChange = { store.sendAction(Action.ChangeTitle(it)) },
            onLoginChange = { store.sendAction(Action.ChangeLogin(it)) },
            onPasswordChange = { store.sendAction(Action.ChangePassword(it)) },
            onTogglePasswordVisibility = { store.sendAction(Action.TogglePasswordVisibility) },
            onValidate = { store.sendAction(Action.Validate) },
            onGenerateButtonClick = onNavigateToPasswordGenerationScreen
        )
    }
}