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
import ru.dimagor555.password.listscreen.PasswordListScreen
import ru.dimagor555.ui.core.theme.PasswordManagerTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PasswordManagerTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "List") {
                    composable("List") {
                        PasswordListScreen(
                            navigateToPasswordDetailsScreen = { navController.navigate("Details/$it") },
                            navigateToSettingsScreen = {},
                            navigateToPasswordCreationScreen = {}
                        )
                    }
                    composable(
                        "Details/{passwordId}",
                        arguments = listOf(navArgument("passwordId") { type = NavType.IntType })
                    ) {
                        PasswordDetailsScreen(
                            navigateBack = { navController.popBackStack() },
                            navigateToPasswordEditingScreen = {}
                        )
                    }
                    composable("Creation") {
                        PasswordCreationScreen(
                            onGeneratePassword = { null },
                            navigateBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}