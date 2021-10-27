package ru.dimagor555.encryption.android

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import ru.dimagor555.encryption.core.AlgorithmProperties
import ru.dimagor555.encryption.domain.Base64
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

internal class EncryptedPrefsSecretKeyProvider(
    applicationContext: Context,
    private val secretKeyGenerator: AesSecretKeyGenerator = AesSecretKeyGenerator(),
    private val base64: Base64 = AndroidBase64()
) {
    private val masterKey = MasterKey.Builder(applicationContext)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .setRequestStrongBoxBacked(true)
        .build()

    private val encryptedPrefs = EncryptedSharedPreferences.create(
        applicationContext,
        ENCRYPTED_PREFS_FILE_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun provideKey(): SecretKey {
        if (!hasKey())
            createKey()
        return getKey()
    }

    private fun hasKey() = encryptedPrefs.contains(ENCRYPTION_KEY_PREFS_KEY)

    private fun createKey() = putEncodedKeyInPrefs(generateEncodedKey())

    private fun generateEncodedKey(): String {
        val secretKey = secretKeyGenerator.generateKey()
        val keyBytes = secretKey.encoded
        return base64.encodeToString(keyBytes)
    }

    private fun putEncodedKeyInPrefs(encodedKey: String) {
        with(encryptedPrefs.edit()) {
            putString(ENCRYPTION_KEY_PREFS_KEY, encodedKey)
            apply()
        }
    }

    private fun getKey(): SecretKey {
        val keyBytes = getKeyBytesFromPrefs()
        return SecretKeySpec(keyBytes, AlgorithmProperties.AES_ALGORITHM)
    }

    private fun getKeyBytesFromPrefs(): ByteArray {
        val encodedKey = getEncodedKeyFromPrefs()
        return base64.decodeFromString(encodedKey)
    }

    private fun getEncodedKeyFromPrefs() = encryptedPrefs.getString(ENCRYPTION_KEY_PREFS_KEY, null)
        ?: error("EncryptedSharedPreferences does not contain encryption key")

    companion object {
        private const val ENCRYPTED_PREFS_FILE_NAME = "encryptedSharedPreferences"
        private const val ENCRYPTION_KEY_PREFS_KEY = "encryptionKey"
    }
}