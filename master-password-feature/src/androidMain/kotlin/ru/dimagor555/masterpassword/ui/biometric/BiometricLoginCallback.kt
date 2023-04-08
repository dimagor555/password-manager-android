package ru.dimagor555.masterpassword.ui.biometric

import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.CryptoObject

internal class BiometricLoginCallback(
    private val onSuccess: (CryptoObject?) -> Unit,
    private val onFail: () -> Unit,
) : BiometricPrompt.AuthenticationCallback() {

    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) =
        onFail()

    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) =
        onSuccess(result.cryptoObject)

    override fun onAuthenticationFailed() =
        onFail()
}