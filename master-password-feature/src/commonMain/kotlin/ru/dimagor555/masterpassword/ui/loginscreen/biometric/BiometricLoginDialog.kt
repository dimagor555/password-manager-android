package ru.dimagor555.masterpassword.ui.loginscreen.biometric

import androidx.compose.runtime.Composable

@Composable
internal expect fun BiometricLoginDialog(
    onSuccess: () -> Unit,
    onDismiss: () -> Unit,
)