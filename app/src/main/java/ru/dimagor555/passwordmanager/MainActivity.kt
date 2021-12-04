package ru.dimagor555.passwordmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import ru.dimagor555.password.creationscreen.PasswordCreationScreen
import ru.dimagor555.password.detailsscreen.PasswordDetailsScreen
import ru.dimagor555.password.editingscreen.PasswordEditingScreen
import ru.dimagor555.password.listscreen.PasswordListScreen
import ru.dimagor555.passwordgenerator.screen.PasswordGenerationScreen
import ru.dimagor555.ui.core.theme.PasswordManagerTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PasswordManagerTheme {
                val navController = rememberNavController()
                /*
                 FIXME: 04.12.2021 I'm strongly recommend to use constant or enum as you alraedy
                        did with password length, for example
                 */
                NavHost(navController = navController, startDestination = "List") {
                    composable("List") {
                        PasswordListScreen(
                            navigateToPasswordDetailsScreen = { navController.navigate("Details/$it") },
                            navigateToSettingsScreen = {},
                            navigateToPasswordCreationScreen = { navController.navigate("Creation") }
                        )
                    }
                    composable(
                        "Details/{passwordId}",
                        arguments = listOf(navArgument("passwordId") { type = NavType.IntType })
                    ) {
                        val passwordId = it.arguments?.getInt("passwordId")
                            ?: error("passwordId argument is not passed")
                        PasswordDetailsScreen(
                            navigateBack = { navController.popBackStack() },
                            navigateToPasswordEditingScreen = { navController.navigate("Editing/$passwordId") }
                        )
                    }
                    composable("Creation") {
                        PasswordCreationScreen(
                            onNavigateToPasswordGenerationScreen = { navController.navigate("Generation") },
                            navigateBack = { navController.popBackStack() }
                        )
                    }
                    composable(
                        "Editing/{passwordId}",
                        arguments = listOf(navArgument("passwordId") { type = NavType.IntType })
                    ) {
                        PasswordEditingScreen(
                            onNavigateToPasswordGenerationScreen = { navController.navigate("Generation") },
                            navigateBack = { navController.popBackStack() }
                        )
                    }
                    composable("Generation") {
                        PasswordGenerationScreen(
                            onNavigateBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}