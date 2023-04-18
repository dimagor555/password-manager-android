package ru.dimagor555.encryption.asymmetric.data.key

import ru.dimagor555.encryption.asymmetric.data.AsymmetricEncryptionProperties
import ru.dimagor555.encryption.asymmetric.domain.key.AsymmetricPublicKey
import saschpe.kase64.base64DecodedBytes
import saschpe.kase64.base64Encoded
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec

@JvmInline
internal value class AsymmetricPublicKeyImpl(
    val publicKey: PublicKey
) : AsymmetricPublicKey {

    override val base64Encoded: String
        get() = publicKey.encoded.base64Encoded
}

val AsymmetricPublicKey.publicKey: PublicKey
    get() = when (this) {
        is AsymmetricPublicKeyImpl -> this.publicKey
        else -> error("custom asymmetric key type is not supported")
    }

fun AsymmetricPublicKey.Companion.fromBase64(encodedKey: String): AsymmetricPublicKey {
    val base64publicKey = encodedKey.base64DecodedBytes
    val keySpec = X509EncodedKeySpec(base64publicKey)
    val keyFactory = KeyFactory.getInstance(AsymmetricEncryptionProperties.ALGORITHM)
    return AsymmetricPublicKeyImpl(keyFactory.generatePublic(keySpec))
}