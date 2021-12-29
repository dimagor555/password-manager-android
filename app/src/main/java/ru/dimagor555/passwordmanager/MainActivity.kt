package ru.dimagor555.passwordmanager

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.*
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import ru.dimagor555.masterpassword.editscreen.EditMasterPasswordScreen
import ru.dimagor555.masterpassword.loginscreen.LoginScreen
import ru.dimagor555.password.creationscreen.PasswordCreationScreen
import ru.dimagor555.password.detailsscreen.PasswordDetailsScreen
import ru.dimagor555.password.editingscreen.PasswordEditingScreen
import ru.dimagor555.password.listscreen.PasswordListScreen
import ru.dimagor555.passwordgenerator.screen.PasswordGenerationScreen
import ru.dimagor555.passwordmanager.startscreen.WelcomeScreen
import ru.dimagor555.passwordmanager.startscreen.hasMasterPassword
import ru.dimagor555.ui.core.theme.PasswordManagerTheme

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        setContent {
            PasswordManagerTheme {
                val startDestination by determineStartDestination()
                splashScreen.setKeepVisibleCondition { startDestination == null }
                startDestination?.let { AppNavHost(it) }
            }
        }
    }

    @Composable
    private fun determineStartDestination(): State<String?> {
        val startDestinationState = remember { mutableStateOf<String?>(null) }
        val hasMasterPassword by hasMasterPassword()
        LaunchedEffect(key1 = hasMasterPassword) {
            when (hasMasterPassword) {
                true -> startDestinationState.value = "Login"
                false -> startDestinationState.value = "Welcome"
            }
        }
        return startDestinationState
    }

    @Composable
    private fun AppNavHost(startDestination: String) {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = startDestination) {
            composable("Welcome") {
                WelcomeScreen(
                    onNavigateNext = { navController.navigate("EditMasterPassword") }
                )
            }
            composable("Login") {
                LoginScreen(
                    onSuccessfulLogin = {
                        navController.popBackStack()
                        navController.navigate("List")
                    }
                )
            }
            composable("EditMasterPassword") {
                EditMasterPasswordScreen(
                    onSuccess = {
                        navController.popBackStack()
                        navController.navigate("List")
                    },
                    onCancel = { navController.popBackStack() },
                    onNavigateToPasswordGenerationScreen = { navController.navigate("Generation") }
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