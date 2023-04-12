package ru.dimagor555.password.ui.createscreen

import androidx.compose.runtime.*
import ru.dimagor555.password.domain.password.encryptedPassword
import ru.dimagor555.res.core.MR
import ru.dimagor555.password.ui.commoneditscreen.CommonEditPasswordScreen
import ru.dimagor555.ui.core.util.stringResource

@Composable
fun CreatePasswordScreen(component: CreatePasswordComponent) {
    component as CreatePasswordComponentImpl

    val state by component.passwordState.collectAsState()

    CommonEditPasswordScreen(
        store = component.commonEditPasswordStore,
        generatedPassword = component.commonEditPasswordState.value.passwordFields.encryptedPassword.text,
        topAppBarTitle = stringResource(MR.strings.create),
        onNavigateToPasswordGenerationScreen = component.callbacks.onNavigateToPasswordGenerationScreen ,
        onNavigateBack = component.callbacks.onNavigateBack,
    ) {  }
    LaunchedEffect(state.isExitScreen) {
        if (state.isExitScreen) {
            component.callbacks.onNavigateBack()
        }
    }
}