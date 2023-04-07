package ru.dimagor555.masterpassword.ui.biometric

internal expect fun BiometricSetUpDialog(
    visible: Boolean,
    onSuccess: () -> Unit,
    onDismiss: () -> Unit,
)