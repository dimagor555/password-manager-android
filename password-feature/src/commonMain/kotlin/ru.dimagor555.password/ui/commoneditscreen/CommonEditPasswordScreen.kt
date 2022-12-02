package ru.dimagor555.password.ui.commoneditscreen

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ru.dimagor555.password.domain.password.field.LOGIN_FIELD_KEY
import ru.dimagor555.password.domain.password.field.PASSWORD_FIELD_KEY
import ru.dimagor555.password.domain.password.field.PHONE_FIELD_KEY
import ru.dimagor555.password.domain.password.field.TITLE_FIELD_KEY
import ru.dimagor555.password.ui.commoneditscreen.component.CommonEditPasswordScreenContent
import ru.dimagor555.password.ui.commoneditscreen.component.CommonEditPasswordTopAppBar
import ru.dimagor555.password.ui.commoneditscreen.model.CommonEditPasswordStore
import ru.dimagor555.password.ui.commoneditscreen.model.CommonEditPasswordStore.Action
import ru.dimagor555.ui.core.component.SingleSnackbarScaffold

@Composable
fun CommonEditPasswordScreen(
    store: CommonEditPasswordStore,
    generatedPassword: String?,
    topAppBarTitle: String,
    onNavigateToPasswordGenerationScreen: () -> Unit,
    onNavigateBack: () -> Unit,
    content: @Composable (snackbarHostState: SnackbarHostState) -> Unit,
) {
    val state by store.state.collectAsState()

    LaunchedEffect(generatedPassword) {
        if (generatedPassword != null)
            store.sendAction(Action.ChangeFieldByKey(PASSWORD_FIELD_KEY, generatedPassword))
    }
    SingleSnackbarScaffold(
        topBar = {
            CommonEditPasswordTopAppBar(
                title = topAppBarTitle,
                onValidate = { store.sendAction(Action.Validate) },
                onNavigateBack = onNavigateBack,
            )
        }
    ) { _, onShowSnackbar ->
        CommonEditPasswordScreenContent(
            state = state,
            onTitleChange = {
                store.sendAction(Action.ChangeFieldByKey(TITLE_FIELD_KEY, it))
            },
            onLoginChange = {
                store.sendAction(Action.ChangeFieldByKey(LOGIN_FIELD_KEY, it))
            },
            onPhoneChange = {
                store.sendAction(Action.ChangeFieldByKey(PHONE_FIELD_KEY, it))
            },
            onPasswordChange = {
                store.sendAction(Action.ChangeFieldByKey(PASSWORD_FIELD_KEY, it))
            },
            onTogglePasswordVisibility = { store.sendAction(Action.TogglePasswordVisibility) },
            onValidate = { store.sendAction(Action.Validate) },
            onGenerateButtonClick = onNavigateToPasswordGenerationScreen,
        )
        content(onShowSnackbar)
    }
}