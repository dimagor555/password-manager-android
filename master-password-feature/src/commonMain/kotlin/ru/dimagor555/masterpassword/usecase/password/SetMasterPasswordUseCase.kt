package ru.dimagor555.masterpassword.usecase.password

import ru.dimagor555.encryption.symmetric.SymmetricEncryptionApi
import ru.dimagor555.hashing.domain.PasswordHasher
import ru.dimagor555.masterpassword.usecase.biometric.repository.BiometricSymmetricKeyRepository
import ru.dimagor555.masterpassword.usecase.password.repository.MasterPasswordHashRepository

internal class SetMasterPasswordUseCase(
    private val hasher: PasswordHasher,
    private val masterPasswordHashRepository: MasterPasswordHashRepository,
    private val biometricSymmetricKeyRepository: BiometricSymmetricKeyRepository,
    private val symmetricEncryptionApi: SymmetricEncryptionApi,
) {

    suspend operator fun invoke(password: String) {
        val passwordHash = hasher.hash(password)
        biometricSymmetricKeyRepository.set(null)
        masterPasswordHashRepository.setPasswordHash(passwordHash)
        symmetricEncryptionApi.setKeyFromPassword(password)
    }
}