package ru.dimagor555.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import ru.dimagor555.masterpassword.ui.editscreen.EditMasterPasswordScreen
import ru.dimagor555.masterpassword.ui.loginscreen.LoginScreen
import ru.dimagor555.masterpassword.ui.startscreen.WelcomeScreen
import ru.dimagor555.password.ui.createscreen.CreatePasswordScreen
import ru.dimagor555.password.ui.detailsscreen.PasswordDetailsScreen
import ru.dimagor555.password.ui.editscreen.EditPasswordScreen
import ru.dimagor555.password.ui.listscreen.PasswordListScreen
import ru.dimagor555.passwordgeneration.ui.screen.PasswordGenerationScreen

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun PasswordManagerRootScreen(component: RootComponent, onShowFirstScreen: () -> Unit) {
    Children(stack = component.childStack) {
        when (val child = it.instance) {
            is RootComponent.Child.Welcome -> WelcomeScreen(child.component)
            is RootComponent.Child.Login -> LoginScreen(child.component)
            is RootComponent.Child.Generation -> PasswordGenerationScreen(child.component)
            is RootComponent.Child.EditMaster -> EditMasterPasswordScreen(child.component)
            is RootComponent.Child.PasswordList -> PasswordListScreen(child.component)
            is RootComponent.Child.EditPassword -> EditPasswordScreen(child.component)
            is RootComponent.Child.PasswordDetails -> PasswordDetailsScreen(child.component)
            is RootComponent.Child.CreatePassword -> CreatePasswordScreen(child.component)
        }
    }
    LaunchedEffect(Unit) {
        onShowFirstScreen()
    }
}