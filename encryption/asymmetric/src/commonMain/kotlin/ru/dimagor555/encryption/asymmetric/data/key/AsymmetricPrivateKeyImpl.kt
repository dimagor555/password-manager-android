package ru.dimagor555.encryption.asymmetric.data.key

import ru.dimagor555.encryption.asymmetric.domain.key.AsymmetricPrivateKey
import java.security.PrivateKey

@JvmInline
internal value class AsymmetricPrivateKeyImpl(
    val privateKey: PrivateKey
) : AsymmetricPrivateKey

internal val AsymmetricPrivateKey.privateKey: PrivateKey
    get() = (this as AsymmetricPrivateKeyImpl).privateKey