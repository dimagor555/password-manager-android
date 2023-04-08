package ru.dimagor555.encryption.symmetric.data.aes

import ru.dimagor555.encryption.symmetric.data.SymmetricEncryptionProperties.IV_SIZE_BYTES
import ru.dimagor555.encryption.symmetric.data.SymmetricEncryptionProperties.TAG_SIZE_BITS
import ru.dimagor555.encryption.symmetric.data.SymmetricEncryptionProperties.TRANSFORMATION
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

class AesGcmCipher(
    private val getCipher: () -> Cipher = { Cipher.getInstance(TRANSFORMATION) },
    private val getKey: () -> SecretKey,
    private val isGenerateIV: Boolean = true,
) {

    val cipher by lazy { getCipher() }

    fun initForEncryption() {
        val params = if (isGenerateIV) createGcmParameterSpec() else null
        cipher.init(Cipher.ENCRYPT_MODE, getKey(), params)
    }

    private fun createGcmParameterSpec(iv: ByteArray = generateRandomIv()): GCMParameterSpec =
        GCMParameterSpec(TAG_SIZE_BITS, iv)

    private fun generateRandomIv() = ByteArray(IV_SIZE_BYTES)
        .also { SecureRandom().nextBytes(it) }

    fun encrypt(input: String): String {
        val encryptedBytes = cipher.doFinal(input.toByteArray())
        return AesEncryptedValue(iv = cipher.iv, bytes = encryptedBytes).toBase64String()
    }

    fun initForDecryption(input: String) {
        val (iv, _) = AesEncryptedValue(input)
        cipher.init(Cipher.DECRYPT_MODE, getKey(), createGcmParameterSpec(iv))
    }

    fun decrypt(input: String): String {
        val (_, encryptedBytes) = AesEncryptedValue(input)
        val decryptedBytes = cipher.doFinal(encryptedBytes)
        return decryptedBytes.toString(Charsets.UTF_8)
    }
}