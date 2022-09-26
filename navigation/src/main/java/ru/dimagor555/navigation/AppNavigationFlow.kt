package ru.dimagor555.navigation

import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ru.dimagor555.masterpassword.ui.startscreen.observeHasMasterPassword

@Composable
fun AppNavigationFlow(onShowFirstScreen: () -> Unit) {
    val startDestination by determineStartDestination()
    startDestination?.let { AppNavHost(startDestination = it) }
    LaunchedEffect(startDestination) {
        startDestination?.let { onShowFirstScreen() }
    }
}

@Composable
private fun determineStartDestination(): State<String?> {
    val startDestinationState = remember { mutableStateOf<String?>(null) }
    val hasMasterPassword by observeHasMasterPassword()
    LaunchedEffect(hasMasterPassword) {
        when (hasMasterPassword) {
            true -> startDestinationState.value = MasterPasswordScreen.Login.route
            false -> startDestinationState.value = MasterPasswordScreen.Welcome.route
            else -> {}
        }
    }
    return startDestinationState
}

@Composable
private fun AppNavHost(startDestination: String) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {
        registerAppFlow(navController)
    }
}

private fun NavGraphBuilder.registerAppFlow(navController: NavController) {
    registerPasswordFeatureFlow(navController)
    registerMasterPasswordFeatureFlow(navController)
    registerPasswordGenerationFeatureFlow(navController)
}