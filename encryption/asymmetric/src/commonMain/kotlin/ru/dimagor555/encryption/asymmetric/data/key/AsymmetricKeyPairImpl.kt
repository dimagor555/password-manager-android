package ru.dimagor555.encryption.asymmetric.data.key

import ru.dimagor555.encryption.asymmetric.data.AsymmetricEncryptionProperties
import ru.dimagor555.encryption.asymmetric.domain.key.AsymmetricKeyPair
import ru.dimagor555.encryption.asymmetric.domain.key.AsymmetricPrivateKey
import ru.dimagor555.encryption.asymmetric.domain.key.AsymmetricPublicKey
import java.security.KeyPair
import java.security.KeyPairGenerator

internal data class AsymmetricKeyPairImpl(
    override val publicKey: AsymmetricPublicKey,
    override val privateKey: AsymmetricPrivateKey,
) : AsymmetricKeyPair

fun AsymmetricKeyPair.Companion.generateNew(): KeyPair? {
    val generator = KeyPairGenerator.getInstance(AsymmetricEncryptionProperties.ALGORITHM)
    generator.initialize(AsymmetricEncryptionProperties.KEY_SIZE_BITS)
    return generator.genKeyPair()
}