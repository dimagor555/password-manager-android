package ru.dimagor555.encryption.symmetric.data.repository

import ru.dimagor555.encryption.symmetric.domain.SymmetricKey

internal class SymmetricKeyRepository {

    private var symmetricKey: SymmetricKey? = null

    fun get(): SymmetricKey =
        symmetricKey ?: error("no symmetric key")

    fun set(symmetricKey: SymmetricKey) {
        this.symmetricKey = symmetricKey
    }
}