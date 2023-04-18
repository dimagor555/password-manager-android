package ru.dimagor555.synchronization.domain.key

import kotlinx.serialization.Serializable
import ru.dimagor555.encryption.asymmetric.data.key.fromBase64
import ru.dimagor555.encryption.asymmetric.domain.key.AsymmetricPublicKey

@Serializable
@JvmInline
internal value class EncodedAsymmetricPublicKey(
    override val base64Encoded: String,
) : AsymmetricPublicKey

internal fun AsymmetricPublicKey.encoded(): EncodedAsymmetricPublicKey =
    EncodedAsymmetricPublicKey(base64Encoded)

internal fun EncodedAsymmetricPublicKey.decoded(): AsymmetricPublicKey =
    AsymmetricPublicKey.fromBase64(base64Encoded)
