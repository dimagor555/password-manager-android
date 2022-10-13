package ru.dimagor555.password.ui.detailsscreen

sealed interface PasswordDetailsComponent {

    data class PasswordDetailsComponentCallbacks(
        val navigateBack: () -> Unit,
        val navigateToPasswordEditingScreen: (Int) -> Unit,
    )
}