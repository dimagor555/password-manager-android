package ru.dimagor555.encryption.symmetric.data.repository

import javax.crypto.SecretKey

internal class SymmetricKeyRepository {

    private var secretKey: SecretKey? = null

    fun get(): SecretKey =
        secretKey ?: error("no symmetric key")

    fun set(secretKey: SecretKey) {
        this.secretKey = secretKey
    }
}