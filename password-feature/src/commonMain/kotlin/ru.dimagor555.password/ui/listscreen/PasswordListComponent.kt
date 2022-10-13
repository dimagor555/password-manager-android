package ru.dimagor555.password.ui.listscreen

sealed interface PasswordListComponent {

    data class PasswordListComponentCallbacks(
        val navigateToPasswordDetailsScreen: (Int) -> Unit,
        val navigateToSettingsScreen: () -> Unit,
        val navigateToPasswordCreationScreen: () -> Unit,
    )
}