package ru.dimagor555.masterpassword.biometry

import androidx.biometric.BiometricPrompt

internal class BiometryLoginCallback(
    private val onSuccess: () -> Unit,
    private val onFail: () -> Unit
) : BiometricPrompt.AuthenticationCallback() {
    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) = onFail()

    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) =
        onSuccess()

    override fun onAuthenticationFailed() = onFail()

}