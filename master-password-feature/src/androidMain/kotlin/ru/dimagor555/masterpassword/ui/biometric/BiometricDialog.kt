package ru.dimagor555.masterpassword.ui.biometric

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity

@Composable
internal fun BiometricDialog(
    visible: Boolean,
    onOpenDialog: suspend FragmentActivity.() -> Unit,
) {
    val activity = LocalContext.current as FragmentActivity
    LaunchedEffect(visible) {
        if (visible) {
            activity.onOpenDialog()
        }
    }
}