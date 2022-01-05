package ru.dimagor555.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import ru.dimagor555.password.createscreen.CreatePasswordScreen
import ru.dimagor555.password.detailsscreen.PasswordDetailsScreen
import ru.dimagor555.password.editscreen.EditPasswordScreen
import ru.dimagor555.password.listscreen.PasswordListScreen

internal object PasswordScreen {
    object Overview : Screen("PasswordOverview")

    object Create : Screen("CreatePassword")

    object Details : Screen(
        route = "PasswordDetails",
        registryRoute = "PasswordDetails/{${ArgName.PASSWORD_ID}}",
        arguments = listOf(passwordIdArgument)
    )

    object Edit : Screen(
        route = "EditPassword",
        registryRoute = "EditPassword/{${ArgName.PASSWORD_ID}}",
        arguments = listOf(passwordIdArgument)
    )

    private val passwordIdArgument = intNavArgument(ArgName.PASSWORD_ID)

    object ArgName {
        const val PASSWORD_ID = "passwordId"
    }
}

internal fun NavGraphBuilder.registerPasswordFeatureFlow(navController: NavController) {
    registerScreen(PasswordScreen.Overview) {
        PasswordListScreen(
            navigateToPasswordDetailsScreen = { passwordId ->
                navController.navigate("${PasswordScreen.Details.route}/$passwordId")
            },
            navigateToSettingsScreen = {},
            navigateToPasswordCreationScreen = {
                navController.navigate(PasswordScreen.Create.route)
            }
        )
    }
    registerScreen(PasswordScreen.Create) {
        val generatedPassword = it.getGeneratedPassword()
        CreatePasswordScreen(
            generatedPassword = generatedPassword,
            onNavigateToPasswordGenerationScreen = {
                navController.navigate(PasswordGenerationScreen.route)
            },
            onNavigateBack = { navController.popBackStack() }
        )
    }
    registerScreen(PasswordScreen.Details) {
        val passwordId = it.getPasswordId()
        PasswordDetailsScreen(
            passwordId = passwordId,
            navigateBack = { navController.popBackStack() },
            navigateToPasswordEditingScreen = {
                navController.navigate("${PasswordScreen.Edit.route}/$passwordId")
            }
        )
    }
    registerScreen(PasswordScreen.Edit) {
        val passwordId = it.getPasswordId()
        val generatedPassword = it.getGeneratedPassword()
        EditPasswordScreen(
            passwordId = passwordId,
            generatedPassword = generatedPassword,
            onNavigateToPasswordGenerationScreen = {
                navController.navigate(PasswordGenerationScreen.route)
            },
            onNavigateBack = { navController.popBackStack() }
        )
    }
}

private fun NavBackStackEntry.getPasswordId() =
    getArgument<Int>(PasswordScreen.ArgName.PASSWORD_ID)