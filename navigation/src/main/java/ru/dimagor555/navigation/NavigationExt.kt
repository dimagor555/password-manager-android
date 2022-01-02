package ru.dimagor555.navigation

import androidx.navigation.*

@NavDestinationDsl
internal fun intNavArgument(
    name: String,
    builder: NavArgumentBuilder.() -> Unit = {}
) = navArgument(name) {
    type = NavType.IntType
    builder()
}

internal inline fun <reified T> NavBackStackEntry.getArgument(name: String): T {
    val arguments = arguments ?: error("arguments are null")
    val argument = arguments.get(name)
    if (argument is T)
        return argument
    else
        error("$name argument is not passed or specified incorrect type")
}

internal fun NavController.putResultString(name: String, value: String?) =
    previousBackStackEntry?.arguments?.putString(name, value)
