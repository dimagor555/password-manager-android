package ru.dimagor555.encryption.asymmetric.data.rsa

import ru.dimagor555.encryption.asymmetric.data.key.privateKey
import ru.dimagor555.encryption.asymmetric.data.key.publicKey
import ru.dimagor555.encryption.asymmetric.domain.AsymmetricDecryptor
import ru.dimagor555.encryption.asymmetric.domain.AsymmetricEncryptor
import ru.dimagor555.encryption.asymmetric.domain.key.AsymmetricPrivateKey
import ru.dimagor555.encryption.asymmetric.domain.key.AsymmetricPublicKey

internal class RsaEncryptor : AsymmetricEncryptor {

    override fun encrypt(
        plaintext: String,
        asymmetricPublicKey: AsymmetricPublicKey,
    ): String =
        EncryptRsaCipher(asymmetricPublicKey.publicKey)
            .encrypt(plaintext)
}

internal class RsaDecryptor : AsymmetricDecryptor {

    override fun decrypt(
        ciphertext: String,
        asymmetricPrivateKey: AsymmetricPrivateKey,
    ): String =
        DecryptRsaCipher(asymmetricPrivateKey.privateKey)
            .decrypt(ciphertext)
}