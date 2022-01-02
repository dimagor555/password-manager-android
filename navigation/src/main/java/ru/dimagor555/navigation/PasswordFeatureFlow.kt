package ru.dimagor555.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import ru.dimagor555.password.creationscreen.PasswordCreationScreen
import ru.dimagor555.password.detailsscreen.PasswordDetailsScreen
import ru.dimagor555.password.editingscreen.PasswordEditingScreen
import ru.dimagor555.password.listscreen.PasswordListScreen

internal object PasswordScreen {
    object Overview : Screen("PasswordOverview")

    object Creation : Screen("PasswordCreation")

    object Details : Screen(
        route = "PasswordDetails",
        registryRoute = "PasswordDetails/{${ArgName.PASSWORD_ID}}",
        arguments = listOf(passwordIdArgument)
    )

    object Editing : Screen(
        route = "PasswordEditing",
        registryRoute = "PasswordEditing/{${ArgName.PASSWORD_ID}}",
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
                navController.navigate(PasswordScreen.Creation.route)
            }
        )
    }
    registerScreen(PasswordScreen.Creation) {
        val generatedPassword = it.getGeneratedPassword()
        PasswordCreationScreen(
            generatedPassword = generatedPassword,
            onNavigateToPasswordGenerationScreen = {
                navController.navigate(PasswordGenerationScreen.route)
            },
            navigateBack = { navController.popBackStack() }
        )
    }
    registerScreen(PasswordScreen.Details) {
        val passwordId = it.getPasswordId()
        PasswordDetailsScreen(
            passwordId = passwordId,
            navigateBack = { navController.popBackStack() },
            navigateToPasswordEditingScreen = {
                navController.navigate("${PasswordScreen.Editing.route}/$passwordId")
            }
        )
    }
    registerScreen(PasswordScreen.Editing) {
        val passwordId = it.getPasswordId()
        val generatedPassword = it.getGeneratedPassword()
        PasswordEditingScreen(
            passwordId = passwordId,
            generatedPassword = generatedPassword,
            onNavigateToPasswordGenerationScreen = {
                navController.navigate(PasswordGenerationScreen.route)
            },
            navigateBack = { navController.popBackStack() }
        )
    }
}

private fun NavBackStackEntry.getPasswordId() =
    getArgument<Int>(PasswordScreen.ArgName.PASSWORD_ID)