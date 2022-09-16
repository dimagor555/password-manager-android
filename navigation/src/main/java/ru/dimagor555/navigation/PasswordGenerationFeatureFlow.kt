package ru.dimagor555.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import ru.dimagor555.passwordgeneration.ui.screen.PasswordGenerationScreen

internal object PasswordGenerationScreen : Screen("PasswordGenerationScreen") {
    object ResultName {
        const val GENERATED_PASSWORD = "generatedPassword"
    }
}

internal fun NavGraphBuilder.registerPasswordGenerationFeatureFlow(navController: NavController) {
    registerScreen(PasswordGenerationScreen) {
        PasswordGenerationScreen { generatedPassword ->
            navController.putResultString(
                PasswordGenerationScreen.ResultName.GENERATED_PASSWORD,
                generatedPassword
            )
            navController.popBackStack()
        }
    }
}

internal fun NavBackStackEntry.getGeneratedPassword() =
    getArgument<String?>(PasswordGenerationScreen.ResultName.GENERATED_PASSWORD)