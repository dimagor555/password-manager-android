package ru.dimagor555.encryption.symmetric.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dimagor555.encryption.symmetric.data.hasher.SHA256Hasher
import ru.dimagor555.encryption.symmetric.data.key.fromBytes
import ru.dimagor555.encryption.symmetric.data.repository.SymmetricKeyRepository
import ru.dimagor555.encryption.symmetric.domain.SymmetricKey

internal class SetSymmetricKeyFromPasswordUsecase(
    private val hasher: SHA256Hasher,
    private val keyRepository: SymmetricKeyRepository,
) {

    suspend operator fun invoke(password: String) = withContext(Dispatchers.Default) {
        val passwordHash = hasher.hash(password)
        val symmetricKey = SymmetricKey.fromBytes(passwordHash)
        keyRepository.set(symmetricKey)
    }
}