package ru.dimagor555.masterpassword.ui.biometric

import android.content.Context
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.CryptoObject
import androidx.fragment.app.FragmentActivity
import dev.icerock.moko.resources.desc.desc
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.get
import ru.dimagor555.masterpassword.data.biometric.BiometricConstants
import ru.dimagor555.masterpassword.usecase.LoginByBiometricUsecase
import ru.dimagor555.masterpassword.usecase.SetUpBiometricLoginUsecase
import ru.dimagor555.res.core.MR
import javax.crypto.Cipher
import kotlin.coroutines.resume

internal suspend fun FragmentActivity.loginByBiometric(
    onSuccess: () -> Unit,
    onFail: () -> Unit,
) {
    val loginByBiometric: LoginByBiometricUsecase = get()
    val result = loginByBiometric(
        params = LoginByBiometricUsecase.Params(
            authorizeCipher = { cipher ->
                authorizeBiometricCipher(
                    cipher = cipher,
                    promptInfo = createLoginPromptInfo(),
                )
            },
        )
    )
    return when (result) {
        is LoginByBiometricUsecase.Result.Success -> onSuccess()
        is LoginByBiometricUsecase.Result.Error -> onFail()
    }
}

private suspend fun FragmentActivity.authorizeBiometricCipher(
    cipher: Cipher,
    promptInfo: BiometricPrompt.PromptInfo,
): Cipher? = withContext(Dispatchers.Main) {
    suspendCancellableCoroutine { continuation ->
        val prompt = createBiometricPrompt(
            onSuccess = { continuation.resume(it?.cipher) },
            onFail = { continuation.resume(null) },
        )
        continuation.invokeOnCancellation { prompt.cancelAuthentication() }
        prompt.authenticate(promptInfo, CryptoObject(cipher))
    }
}

private fun FragmentActivity.createBiometricPrompt(
    onSuccess: (CryptoObject?) -> Unit,
    onFail: () -> Unit,
) = BiometricPrompt(
    this,
    BiometricLoginCallback(
        onSuccess = onSuccess,
        onFail = onFail,
    ),
)

private fun Context.createLoginPromptInfo() =
    BiometricPrompt.PromptInfo.Builder()
        .setTitle(MR.strings.biometry_login_dialog_title.desc().toString(this))
        .setNegativeButtonText(MR.strings.biometry_login_cancel_button_text.desc().toString(this))
        .setAllowedAuthenticators(BiometricConstants.AUTHENTICATOR)
        .build()

internal suspend fun FragmentActivity.setUpBiometricLogin(
    onSuccess: () -> Unit,
    onFail: () -> Unit,
) {
    val setUpBiometricLogin: SetUpBiometricLoginUsecase = get()
    val result = setUpBiometricLogin(
        params = SetUpBiometricLoginUsecase.Params(
            authorizeCipher = { cipher ->
                authorizeBiometricCipher(
                    cipher = cipher,
                    promptInfo = createSetUpPromptInfo(),
                )
            },
        )
    )
    return when (result) {
        is SetUpBiometricLoginUsecase.Result.Success -> onSuccess()
        is SetUpBiometricLoginUsecase.Result.Error -> onFail()
    }
}

private fun Context.createSetUpPromptInfo() =
    BiometricPrompt.PromptInfo.Builder()
        .setTitle(MR.strings.biometric_set_up_sheet_title.desc().toString(this))
        .setNegativeButtonText(MR.strings.cancel.desc().toString(this))
        .setAllowedAuthenticators(BiometricConstants.AUTHENTICATOR)
        .build()
