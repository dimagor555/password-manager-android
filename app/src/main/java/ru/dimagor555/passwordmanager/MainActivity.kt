package ru.dimagor555.passwordmanager

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import ru.dimagor555.masterpassword.loginscreen.LoginScreen
import ru.dimagor555.password.creationscreen.PasswordCreationScreen
import ru.dimagor555.password.detailsscreen.PasswordDetailsScreen
import ru.dimagor555.password.editingscreen.PasswordEditingScreen
import ru.dimagor555.password.listscreen.PasswordListScreen
import ru.dimagor555.passwordgenerator.screen.PasswordGenerationScreen
import ru.dimagor555.ui.core.theme.PasswordManagerTheme

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            PasswordManagerTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "Login") {
                    composable("Login") {
                        LoginScreen(
                            onSuccessfulLogin = {
                                navController.popBackStack()
                                navController.navigate("List")
                            }
                        )
                    }
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