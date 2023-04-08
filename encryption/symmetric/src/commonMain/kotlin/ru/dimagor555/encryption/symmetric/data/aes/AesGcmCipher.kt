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

    fun encrypt(plaintext: String): String {
        val encryptedBytes = cipher.doFinal(plaintext.toByteArray())
        return AesEncryptedValue(iv = cipher.iv, bytes = encryptedBytes).toBase64String()
    }

    fun initForDecryption(ciphertext: String) {
        val (iv, _) = AesEncryptedValue(ciphertext)
        cipher.init(Cipher.DECRYPT_MODE, getKey(), createGcmParameterSpec(iv))
    }

    fun decrypt(ciphertext: String): String {
        val (_, encryptedBytes) = AesEncryptedValue(ciphertext)
        val decryptedBytes = cipher.doFinal(encryptedBytes)
        return decryptedBytes.toString(Charsets.UTF_8)
    }
}