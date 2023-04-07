package ru.dimagor555.masterpassword.ui.biometric

import androidx.compose.runtime.Composable

@Composable
internal actual fun BiometricSetUpDialog(
    visible: Boolean,
    onSuccess: () -> Unit,
    onDismiss: () -> Unit
) {
    BiometricDialog(
        visible = visible,
        onOpenDialog = { setUpBiometricLogin(onSuccess, onDismiss) },
    )
}