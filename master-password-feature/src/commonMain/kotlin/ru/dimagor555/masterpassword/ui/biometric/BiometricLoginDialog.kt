package ru.dimagor555.masterpassword.ui.biometric

import androidx.compose.runtime.Composable

@Composable
internal expect fun BiometricLoginDialog(
    visible: Boolean,
    onSuccess: () -> Unit,
    onDismiss: () -> Unit,
)