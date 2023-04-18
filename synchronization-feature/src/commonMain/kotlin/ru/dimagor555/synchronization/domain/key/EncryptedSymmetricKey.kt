package ru.dimagor555.synchronization.domain.key

import kotlinx.serialization.Serializable
import ru.dimagor555.encryption.asymmetric.domain.AsymmetricDecryptor
import ru.dimagor555.encryption.asymmetric.domain.AsymmetricEncryptor
import ru.dimagor555.encryption.asymmetric.domain.key.AsymmetricPrivateKey
import ru.dimagor555.encryption.asymmetric.domain.key.AsymmetricPublicKey
import ru.dimagor555.encryption.symmetric.data.key.fromBase64
import ru.dimagor555.encryption.symmetric.domain.SymmetricKey

@Serializable
@JvmInline
internal value class EncryptedSymmetricKey(
    override val base64Encoded: String,
) : SymmetricKey

internal fun SymmetricKey.encrypted(
    encryptor: AsymmetricEncryptor,
    asymmetricPublicKey: AsymmetricPublicKey,
): EncryptedSymmetricKey {
    val encryptedKey = encryptor.encrypt(base64Encoded, asymmetricPublicKey)
    return EncryptedSymmetricKey(encryptedKey)
}

internal fun EncryptedSymmetricKey.decrypted(
    decryptor: AsymmetricDecryptor,
    asymmetricPrivateKey: AsymmetricPrivateKey,
): SymmetricKey {
    val decryptedKey = decryptor.decrypt(base64Encoded, asymmetricPrivateKey)

    return SymmetricKey.fromBase64(decryptedKey)
}
