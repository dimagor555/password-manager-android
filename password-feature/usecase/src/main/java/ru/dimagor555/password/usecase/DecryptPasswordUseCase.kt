package ru.dimagor555.password.usecase

import ru.dimagor555.encryption.domain.Decryptor
import ru.dimagor555.password.repository.PasswordRepository

class DecryptPasswordUseCase(
    private val passwordRepository: PasswordRepository,
    private val decryptor: Decryptor
) {
    suspend operator fun invoke(passwordId: Int): String {
        val password = passwordRepository.getById(passwordId)
        return decryptor.decrypt(password.encryptedPassword)
    }
}