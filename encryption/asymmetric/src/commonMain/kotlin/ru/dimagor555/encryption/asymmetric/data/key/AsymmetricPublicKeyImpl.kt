package ru.dimagor555.encryption.asymmetric.data.key

import ru.dimagor555.encryption.asymmetric.data.AsymmetricEncryptionProperties
import ru.dimagor555.encryption.asymmetric.domain.key.AsymmetricPublicKey
import saschpe.kase64.base64DecodedBytes
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec

@JvmInline
internal value class AsymmetricPublicKeyImpl(
    val publicKey: PublicKey
) : AsymmetricPublicKey

internal val AsymmetricPublicKey.publicKey: PublicKey
    get() = (this as AsymmetricPublicKeyImpl).publicKey

fun AsymmetricPublicKey.Companion.fromBase64(encodedKey: String): PublicKey? {
    val base64publicKey = encodedKey.base64DecodedBytes
    val keySpec = X509EncodedKeySpec(base64publicKey)
    val keyFactory = KeyFactory.getInstance(AsymmetricEncryptionProperties.ALGORITHM)
    return keyFactory.generatePublic(keySpec)
}