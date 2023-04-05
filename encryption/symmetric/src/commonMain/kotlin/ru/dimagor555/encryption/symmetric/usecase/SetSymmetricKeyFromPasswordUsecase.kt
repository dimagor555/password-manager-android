package ru.dimagor555.encryption.symmetric.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dimagor555.encryption.symmetric.data.SymmetricEncryptionProperties
import ru.dimagor555.encryption.symmetric.data.hasher.SHA256Hasher
import ru.dimagor555.encryption.symmetric.data.repository.SymmetricKeyRepository
import javax.crypto.spec.SecretKeySpec

internal class SetSymmetricKeyFromPasswordUsecase(
    private val hasher: SHA256Hasher,
    private val keyRepository: SymmetricKeyRepository,
) {

    suspend operator fun invoke(password: String) = withContext(Dispatchers.Default) {
        val passwordHash = hasher.hash(password)
        val secretKey = SecretKeySpec(
            passwordHash,
            SymmetricEncryptionProperties.ALGORITHM,
        )
        keyRepository.set(secretKey)
    }
}