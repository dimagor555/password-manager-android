package ru.dimagor555.password.ui.createscreen

sealed interface CreatePasswordComponent {

    fun sendGeneratedPassword(generatedPassword: String)

    data class CreatePasswordComponentCallbacks(
        val onNavigateToPasswordGenerationScreen: () -> Unit,
        val onNavigateBack: () -> Unit,
    )
}
