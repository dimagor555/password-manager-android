package ru.dimagor555.encryption.symmetric.data.key

import ru.dimagor555.encryption.symmetric.data.SymmetricEncryptionProperties
import ru.dimagor555.encryption.symmetric.domain.SymmetricKey
import saschpe.kase64.base64DecodedBytes
import saschpe.kase64.base64Encoded
import java.security.SecureRandom
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

@JvmInline
internal value class SymmetricKeyImpl(val secretKey: SecretKey) : SymmetricKey {

    override val base64Encoded: String
        get() = secretKey.encoded.base64Encoded
}

internal val SymmetricKey.secretKey: SecretKey
    get() = when (this) {
        is SymmetricKeyImpl -> this.secretKey
        else -> error("custom symmetric key type is not supported")
    }

fun SymmetricKey.Companion.fromBase64(encodedKey: String): SymmetricKey =
    SymmetricKey.fromBytes(encodedKey.base64DecodedBytes)

internal fun SymmetricKey.Companion.fromBytes(bytes: ByteArray): SymmetricKey {
    val secretKey = SecretKeySpec(
        bytes,
        SymmetricEncryptionProperties.ALGORITHM,
    )
    return SymmetricKeyImpl(secretKey)
}

fun SymmetricKey.generateNew(): SymmetricKey {
    val keyBytes = ByteArray(SymmetricEncryptionProperties.KEY_SIZE_BYTES)
    SecureRandom().apply { nextBytes(keyBytes) }
    return SymmetricKey.fromBytes(keyBytes)
}