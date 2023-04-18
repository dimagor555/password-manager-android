package ru.dimagor555.encryption.asymmetric.data.key

import ru.dimagor555.encryption.asymmetric.domain.key.AsymmetricPrivateKey
import saschpe.kase64.base64Encoded
import java.security.PrivateKey

@JvmInline
internal value class AsymmetricPrivateKeyImpl(
    val privateKey: PrivateKey
) : AsymmetricPrivateKey {

    override val base64Encoded: String
        get() = privateKey.encoded.base64Encoded
}

internal val AsymmetricPrivateKey.privateKey: PrivateKey
    get() = (this as AsymmetricPrivateKeyImpl).privateKey