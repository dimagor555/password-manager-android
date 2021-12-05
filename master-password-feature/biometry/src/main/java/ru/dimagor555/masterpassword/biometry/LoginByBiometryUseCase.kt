package ru.dimagor555.masterpassword.biometry

import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class LoginByBiometryUseCase {
    suspend operator fun invoke(activity: FragmentActivity) = activity.login()

    private suspend fun FragmentActivity.login() =
        withContext(Dispatchers.Main) {
            var isLoginSuccessful: Boolean? = null
            authenticate(
                onSuccess = { isLoginSuccessful = true },
                onFail = { isLoginSuccessful = false }
            )
            while (isLoginSuccessful == null)
                delay(100)
            isLoginSuccessful!!
        }

    private fun FragmentActivity.authenticate(onSuccess: () -> Unit, onFail: () -> Unit) =
        BiometricPrompt(this, BiometryLoginCallback(onSuccess, onFail))
            .run { authenticate(createPromptInfo()) }

    private fun FragmentActivity.createPromptInfo() =
        BiometricPrompt.PromptInfo.Builder()
            .setTitle(getString(R.string.biometry_login_dialog_title))
            .setNegativeButtonText(getString(R.string.biometry_login_cancel_button_text))
            .setAllowedAuthenticators(BiometryRepositoryImpl.DEFAULT_AUTHENTICATOR)
            .build()
}