package ru.dimagor555.masterpassword.ui.editscreen

interface EditMasterPasswordComponent {

    fun sendGeneratedPassword(generatedPassword: String)

    data class EditMasterPasswordCallbacks(
        val onSuccess: () -> Unit,
        val onCancel: () -> Unit,
        val onNavigateToPasswordGenerationScreen: () -> Unit, // TODO remove
    )
}