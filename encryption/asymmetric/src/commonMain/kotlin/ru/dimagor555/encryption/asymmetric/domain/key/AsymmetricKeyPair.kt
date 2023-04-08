package ru.dimagor555.encryption.asymmetric.domain.key

interface AsymmetricKeyPair {

    val publicKey: AsymmetricPublicKey
    val privateKey: AsymmetricPrivateKey

    companion object
}