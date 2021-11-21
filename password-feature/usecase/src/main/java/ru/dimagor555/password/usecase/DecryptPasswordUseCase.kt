package ru.dimagor555.password.usecase

import ru.dimagor555.encryption.domain.Decryptor
import ru.dimagor555.password.repository.PasswordRepository
import ru.dimagor555.password.repository.getByIdOrThrowException

class DecryptPasswordUseCase(
    private val passwordRepository: PasswordRepository,
    private val decryptor: Decryptor
) {
    suspend operator fun invoke(passwordId: Int): String {
        val password = passwordRepository.getByIdOrThrowException(passwordId)
        return decryptor.decrypt(password.encryptedPassword)
    }
}