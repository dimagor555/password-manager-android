package ru.dimagor555.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import ru.dimagor555.masterpassword.ui.editscreen.EditMasterPasswordScreen
import ru.dimagor555.masterpassword.ui.loginscreen.LoginScreen
import ru.dimagor555.masterpassword.ui.startscreen.WelcomeScreen

internal object MasterPasswordScreen {
    object Welcome : Screen("Welcome")

    object Login : Screen("Login")

    object Edit : Screen("EditMasterPassword")
}

internal fun NavGraphBuilder.registerMasterPasswordFeatureFlow(navController: NavController) {
    registerScreen(MasterPasswordScreen.Welcome) {
        WelcomeScreen(
            onNavigateNext = { navController.navigate(MasterPasswordScreen.Edit.route) }
        )
    }
    registerScreen(MasterPasswordScreen.Login) {
        LoginScreen(
            onSuccessfulLogin = {
                navController.popBackStack()
                navController.navigate(PasswordScreen.Overview.route)
            }
        )
    }
    registerScreen(MasterPasswordScreen.Edit) {
        val generatedPassword = it.getGeneratedPassword()
        EditMasterPasswordScreen(
            generatedPassword = generatedPassword,
            onSuccess = {
                navController.popBackStack()
                navController.navigate(PasswordScreen.Overview.route)
            },
            onCancel = { navController.popBackStack() },
            onNavigateToPasswordGenerationScreen = {
                navController.navigate(PasswordGenerationScreen.route)
            }
        )
    }
}