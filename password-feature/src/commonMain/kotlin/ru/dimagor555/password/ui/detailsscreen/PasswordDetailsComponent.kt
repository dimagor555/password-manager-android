package ru.dimagor555.password.ui.detailsscreen

interface PasswordDetailsComponent {

    data class PasswordDetailsComponentCallbacks(
        val navigateBack: () -> Unit,
        val navigateToPasswordEditingScreen: (String) -> Unit,
    )
}