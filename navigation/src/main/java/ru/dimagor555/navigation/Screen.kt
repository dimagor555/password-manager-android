package ru.dimagor555.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

internal sealed class Screen(
    val route: String,
    val registryRoute: String = route,
    val arguments: List<NamedNavArgument> = emptyList()
)

internal fun NavGraphBuilder.registerScreen(
    screen: Screen,
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(route = screen.registryRoute, arguments = screen.arguments, content = content)
}