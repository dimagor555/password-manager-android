package ru.dimagor555.encryption.symmetric.data.aes

import ru.dimagor555.encryption.symmetric.data.SymmetricEncryptionProperties.IV_SIZE_BYTES
import ru.dimagor555.encryption.symmetric.data.SymmetricEncryptionProperties.TAG_SIZE_BITS
import ru.dimagor555.encryption.symmetric.data.SymmetricEncryptionProperties.TRANSFORMATION
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

internal class AesGcmCipher(
    private val getKey: () -> SecretKey,
) {

    fun encrypt(input: String): String {
        val cipher = getCipher()
        val encryptedBytes = cipher.encrypt(input.toByteArray())
        return AesEncryptedValue(iv = cipher.iv, bytes = encryptedBytes).toBase64String()
    }

    private fun getCipher() = Cipher.getInstance(TRANSFORMATION)

    private fun Cipher.encrypt(input: ByteArray): ByteArray {
        init(Cipher.ENCRYPT_MODE, getKey(), createGcmParameterSpec())
        return doFinal(input)
    }

    private fun createGcmParameterSpec(iv: ByteArray = generateRandomIv()) =
        GCMParameterSpec(TAG_SIZE_BITS, iv)

    private fun generateRandomIv() = ByteArray(IV_SIZE_BYTES)
        .also { SecureRandom().nextBytes(it) }

    fun decrypt(input: String): String {
        val (iv, encryptedBytes) = AesEncryptedValue(input)
        val cipher = getCipher()
        val decryptedBytes = cipher.decrypt(encryptedBytes, iv)
        return decryptedBytes.toString(Charsets.UTF_8)
    }

    private fun Cipher.decrypt(input: ByteArray, iv: ByteArray): ByteArray {
        init(Cipher.DECRYPT_MODE, getKey(), createGcmParameterSpec(iv))
        return doFinal(input)
    }
}