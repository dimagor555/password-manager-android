package ru.dimagor555.masterpassword.data.biometric

import android.annotation.SuppressLint
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import ru.dimagor555.encryption.symmetric.data.SymmetricEncryptionProperties
import java.security.KeyStore
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

internal class BiometricSecretKeyProvider {

    fun getOrCreate(): SecretKey =
        getSecretKeyFromKeystoreOrNull()
            ?: createNewSecretKey()

    private fun getSecretKeyFromKeystoreOrNull(): SecretKey? {
        val key = KeyStore
            .getInstance(ANDROID_KEYSTORE)
            .run {
                load(null)
                getKey(KEY_NAME, null)
            }
        return key as? SecretKey
    }

    private fun createNewSecretKey(): SecretKey =
        createKeyGenerator(createKeyGenParams()).generateKey()

    private fun createKeyGenerator(keyGenParams: KeyGenParameterSpec): KeyGenerator =
        KeyGenerator
            .getInstance(SymmetricEncryptionProperties.ALGORITHM, ANDROID_KEYSTORE)
            .apply { init(keyGenParams) }

    @SuppressLint("WrongConstant")
    private fun createKeyGenParams(): KeyGenParameterSpec =
        KeyGenParameterSpec.Builder(
            KEY_NAME,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT,
        ).apply {
            setBlockModes(SymmetricEncryptionProperties.MODE)
            setEncryptionPaddings(SymmetricEncryptionProperties.PADDING)
            setKeySize(SymmetricEncryptionProperties.KEY_SIZE_BITS)
            setUserAuthenticationRequired(true)
        }.build()

    companion object {
        private const val KEY_NAME = "biometricSecretKey"
        private const val ANDROID_KEYSTORE = "AndroidKeyStore"
    }
}