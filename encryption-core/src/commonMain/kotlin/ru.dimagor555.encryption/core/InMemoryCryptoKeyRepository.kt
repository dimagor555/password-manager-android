package ru.dimagor555.encryption.core

import ru.dimagor555.encryption.domain.CryptoKey
import ru.dimagor555.encryption.domain.CryptoKeyRepository
import ru.dimagor555.encryption.domain.NoCryptoKeyException

internal class InMemoryCryptoKeyRepository : CryptoKeyRepository {
    private var cryptoKey: CryptoKey? = null

    override fun setKey(cryptoKey: CryptoKey) = run { this.cryptoKey = cryptoKey }

    override fun getKey() = cryptoKey ?: throw NoCryptoKeyException()
}