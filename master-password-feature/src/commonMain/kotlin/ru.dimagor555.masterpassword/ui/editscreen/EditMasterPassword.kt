package ru.dimagor555.masterpassword.ui.editscreen

sealed interface EditMasterPassword {

    data class EditMasterPasswordCallbacks(
        val onSuccess: () -> Unit,
        val onCancel: () -> Unit,
        val onNavigateToPasswordGenerationScreen: () -> Unit,
    )
}