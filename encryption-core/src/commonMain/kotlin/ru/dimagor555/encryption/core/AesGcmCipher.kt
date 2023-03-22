package ru.dimagor555.encryption.core

import ru.dimagor555.encryption.core.AlgorithmProperties.IV_SIZE_BYTES
import ru.dimagor555.encryption.core.AlgorithmProperties.TAG_SIZE_BITS
import ru.dimagor555.encryption.core.AlgorithmProperties.TRANSFORMATION
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

internal class AesGcmCipher(
    private val key: SecretKey,
) {
    fun encrypt(input: String): String {
        val cipher = getCipher()
        val encryptedBytes = cipher.encrypt(input.toByteArray())
        return EncryptedValue(iv = cipher.iv, bytes = encryptedBytes).toBase64String()
    }

    private fun getCipher() = Cipher.getInstance(TRANSFORMATION)

    private fun Cipher.encrypt(input: ByteArray): ByteArray {
        init(Cipher.ENCRYPT_MODE, key, createGcmParameterSpec())
        return doFinal(input)
    }

    private fun createGcmParameterSpec(iv: ByteArray = generateRandomIv()) =
        GCMParameterSpec(TAG_SIZE_BITS, iv)

    private fun generateRandomIv() = ByteArray(IV_SIZE_BYTES)
        .also { SecureRandom().nextBytes(it) }

    fun decrypt(input: String): String {
        val (iv, encryptedBytes) = EncryptedValue(input)
        val cipher = getCipher()
        val decryptedBytes = cipher.decrypt(encryptedBytes, iv)
        return decryptedBytes.toString(Charsets.UTF_8)
    }

    private fun Cipher.decrypt(input: ByteArray, iv: ByteArray): ByteArray {
        init(Cipher.DECRYPT_MODE, key, createGcmParameterSpec(iv))
        return doFinal(input)
    }
}