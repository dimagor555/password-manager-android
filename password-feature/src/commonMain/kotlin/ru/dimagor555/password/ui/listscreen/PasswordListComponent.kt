package ru.dimagor555.password.ui.listscreen

interface PasswordListComponent {

    data class PasswordListComponentCallbacks(
        val navigateToPasswordDetailsScreen: (passwordId: String, parentId: String) -> Unit,
        val navigateToSettingsScreen: () -> Unit,
        val navigateToPasswordCreationScreen: () -> Unit,
    )
}