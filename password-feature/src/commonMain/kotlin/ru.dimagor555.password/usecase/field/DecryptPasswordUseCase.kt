package ru.dimagor555.password.usecase.field

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dimagor555.encryption.domain.Decryptor
import ru.dimagor555.password.domain.password.encryptedPassword
import ru.dimagor555.password.repository.PasswordRepository
import ru.dimagor555.password.repository.getByIdOrThrowException

class DecryptPasswordUseCase(
    private val passwordRepository: PasswordRepository,
    private val decryptor: Decryptor
) {
    suspend operator fun invoke(passwordId: String): String = withContext(Dispatchers.Default) {
        val password = passwordRepository.getByIdOrThrowException(passwordId)
        decryptor.decrypt(password.fields.encryptedPassword.text)
    }
}