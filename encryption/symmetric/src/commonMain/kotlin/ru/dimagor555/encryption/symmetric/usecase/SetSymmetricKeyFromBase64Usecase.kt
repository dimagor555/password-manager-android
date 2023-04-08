package ru.dimagor555.encryption.symmetric.usecase

import ru.dimagor555.encryption.symmetric.data.SymmetricEncryptionProperties
import ru.dimagor555.encryption.symmetric.data.repository.SymmetricKeyRepository
import saschpe.kase64.base64DecodedBytes
import javax.crypto.spec.SecretKeySpec

internal class SetSymmetricKeyFromBase64Usecase(
    private val keyRepository: SymmetricKeyRepository,
) {

    operator fun invoke(encodedKey: String) {
        val secretKey = SecretKeySpec(
            encodedKey.base64DecodedBytes,
            SymmetricEncryptionProperties.ALGORITHM,
        )
        keyRepository.set(secretKey)
    }
}