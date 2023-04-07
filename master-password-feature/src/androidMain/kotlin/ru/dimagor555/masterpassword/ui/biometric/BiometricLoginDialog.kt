package ru.dimagor555.masterpassword.ui.biometric

import androidx.compose.runtime.Composable

@Composable
internal actual fun BiometricLoginDialog(
    visible: Boolean,
    onSuccess: () -> Unit,
    onDismiss: () -> Unit
) {
    BiometricDialog(
        visible = visible,
        onOpenDialog = { loginByBiometric(onSuccess, onDismiss) },
    )
}