package ru.dimagor555.encryption.symmetric.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dimagor555.encryption.symmetric.data.key.fromBytes
import ru.dimagor555.encryption.symmetric.data.repository.SymmetricKeyRepository
import ru.dimagor555.encryption.symmetric.domain.SymmetricKey
import ru.dimagor555.hashing.domain.PasswordHasher
import ru.dimagor555.hashing.domain.Sha256Hasher

internal class SetSymmetricKeyFromPasswordUsecase(
    private val passwordHasher: PasswordHasher,
    private val sha256hasher: Sha256Hasher,
    private val keyRepository: SymmetricKeyRepository,
) {

    suspend operator fun invoke(password: String) = withContext(Dispatchers.Default) {
        val strongHash = passwordHasher.hashForSymmetricKey(password)
        val symmetricKeySizeHash = sha256hasher.hash(strongHash)
        val symmetricKey = SymmetricKey.fromBytes(symmetricKeySizeHash)
        keyRepository.set(symmetricKey)
    }
}