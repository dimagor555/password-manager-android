package ru.dimagor555.password.usecase.field

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dimagor555.encryption.symmetric.domain.SymmetricDecryptor
import ru.dimagor555.password.domain.password.encryptedPassword
import ru.dimagor555.password.usecase.password.repository.PasswordRepository
import ru.dimagor555.password.usecase.password.repository.getByIdOrThrowException

class DecryptPasswordUseCase(
    private val passwordRepository: PasswordRepository,
    private val decryptor: SymmetricDecryptor
) {
    suspend operator fun invoke(passwordId: String): String = withContext(Dispatchers.Default) {
        val password = passwordRepository.getByIdOrThrowException(passwordId)
        decryptor.decrypt(password.fields.encryptedPassword.text)
    }
}