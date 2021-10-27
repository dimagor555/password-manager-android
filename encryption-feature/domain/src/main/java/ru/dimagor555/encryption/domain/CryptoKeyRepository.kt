package ru.dimagor555.encryption.domain

interface CryptoKeyRepository {
    fun setKey(cryptoKey: CryptoKey)

    fun getKey(): CryptoKey
}