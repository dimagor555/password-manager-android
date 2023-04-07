package ru.dimagor555.encryption.symmetric.usecase

import ru.dimagor555.encryption.symmetric.data.repository.SymmetricKeyRepository
import saschpe.kase64.base64Encoded

internal class GetSymmetricKeyBase64Usecase(
    private val keyRepository: SymmetricKeyRepository,
) {

    operator fun invoke(): String =
        keyRepository
            .get()
            .encoded
            .base64Encoded
}