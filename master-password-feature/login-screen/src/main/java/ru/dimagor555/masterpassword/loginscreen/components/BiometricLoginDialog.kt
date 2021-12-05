package ru.dimagor555.masterpassword.loginscreen.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.launch
import ru.dimagor555.masterpassword.biometry.LoginByBiometryUseCase

@Composable
internal fun BiometricLoginDialog(
    onSuccess: () -> Unit,
    onDismiss: () -> Unit
) {
    val activity = LocalContext.current as FragmentActivity
    val coroutineScope = rememberCoroutineScope()
    val loginByBiometry = remember { LoginByBiometryUseCase() }
    LaunchedEffect(key1 = null) {
        coroutineScope.launch {
            if (loginByBiometry(activity))
                onSuccess()
            else
                onDismiss()
        }
    }
}