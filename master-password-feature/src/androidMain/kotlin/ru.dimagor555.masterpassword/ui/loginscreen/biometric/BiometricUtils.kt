package ru.dimagor555.masterpassword.ui.loginscreen.biometric

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import ru.dimagor555.res.core.MR
import ru.dimagor555.ui.core.util.stringResource

@Composable
internal actual fun OnCanUseBiometricLogin(action: () -> Unit) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        if (context.canUseBiometricLogin())
            action()
    }
}

private fun Context.canUseBiometricLogin() = BiometricManager.from(this)
    .run { canAuthenticate(DEFAULT_AUTHENTICATOR) == BiometricManager.BIOMETRIC_SUCCESS }

internal fun FragmentActivity.loginByBiometrics(onSuccess: () -> Unit, onFail: () -> Unit) =
    BiometricPrompt(this, BiometricLoginCallback(onSuccess, onFail))
        .authenticate(createPromptInfo())

private fun Context.createPromptInfo() =
    BiometricPrompt.PromptInfo.Builder()
        .setTitle(stringResource(MR.strings.biometry_login_dialog_title))
        .setNegativeButtonText(stringResource(MR.strings.biometry_login_cancel_button_text))
        .setAllowedAuthenticators(DEFAULT_AUTHENTICATOR)
        .build()

private const val DEFAULT_AUTHENTICATOR = BiometricManager.Authenticators.BIOMETRIC_WEAK