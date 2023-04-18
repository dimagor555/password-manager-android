package ru.dimagor555.encryption.asymmetric.data.key

import ru.dimagor555.encryption.asymmetric.data.AsymmetricEncryptionProperties
import ru.dimagor555.encryption.asymmetric.domain.key.AsymmetricKeyPair
import ru.dimagor555.encryption.asymmetric.domain.key.AsymmetricPrivateKey
import ru.dimagor555.encryption.asymmetric.domain.key.AsymmetricPublicKey
import java.security.KeyPairGenerator

internal data class AsymmetricKeyPairImpl(
    override val publicKey: AsymmetricPublicKey,
    override val privateKey: AsymmetricPrivateKey,
) : AsymmetricKeyPair

fun AsymmetricKeyPair.Companion.generateNew(): AsymmetricKeyPair {
    val generator = KeyPairGenerator.getInstance(AsymmetricEncryptionProperties.ALGORITHM)
    generator.initialize(AsymmetricEncryptionProperties.KEY_SIZE_BITS)
    val keyPair = generator.genKeyPair()
    return AsymmetricKeyPairImpl(
        publicKey = AsymmetricPublicKeyImpl(keyPair.public),
        privateKey = AsymmetricPrivateKeyImpl(keyPair.private)
    )
}