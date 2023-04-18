package ru.dimagor555.encryption.asymmetric.data.rsa

import ru.dimagor555.encryption.asymmetric.data.AsymmetricEncryptionProperties.TRANSFORMATION
import saschpe.kase64.base64DecodedBytes
import saschpe.kase64.base64Encoded
import java.security.PrivateKey
import java.security.PublicKey
import javax.crypto.Cipher

internal class EncryptRsaCipher(
    private val publicKey: PublicKey,
) {

    private val cipher by lazy { Cipher.getInstance(TRANSFORMATION) }

    fun encrypt(input: String): String {
        val encryptedBytes = cipher.encrypt(input.toByteArray())
        return encryptedBytes.base64Encoded
    }

    private fun Cipher.encrypt(input: ByteArray): ByteArray {
        init(Cipher.ENCRYPT_MODE, publicKey)
        return doFinal(input)
    }
}

internal class DecryptRsaCipher(
    private val privateKey: PrivateKey,
) {

    private val cipher by lazy { Cipher.getInstance(TRANSFORMATION) }

    fun decrypt(input: String): String {
        val decryptedBytes = cipher.decrypt(input.base64DecodedBytes)
        return decryptedBytes.toString(Charsets.UTF_8)
    }

    private fun Cipher.decrypt(input: ByteArray): ByteArray {
        init(Cipher.DECRYPT_MODE, privateKey)
        return doFinal(input)
    }
}