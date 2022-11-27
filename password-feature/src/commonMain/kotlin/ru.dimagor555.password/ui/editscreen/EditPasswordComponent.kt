package ru.dimagor555.password.ui.editscreen

interface EditPasswordComponent {

    fun sendGeneratedPassword(generatedPassword: String)

    data class EditPasswordComponentCallbacks(
        val onNavigateToPasswordGenerationScreen: () -> Unit,
        val onNavigateBack: () -> Unit,
    )
}