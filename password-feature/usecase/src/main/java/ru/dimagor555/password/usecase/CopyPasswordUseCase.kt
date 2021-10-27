package ru.dimagor555.password.usecase

import ru.dimagor555.encryption.domain.Decryptor
import ru.dimagor555.password.domain.Password
import ru.dimagor555.password.repository.ClipboardRepository
import ru.dimagor555.password.repository.PasswordRepository

class CopyPasswordUseCase(
    passwordRepository: PasswordRepository,
    clipboardRepository: ClipboardRepository,
    private val decryptor: Decryptor
) : CopyUseCase(passwordRepository, clipboardRepository) {
    override fun getTextToCopy(password: Password) = decryptPassword(password)

    private fun decryptPassword(password: Password) =
        decryptor.decrypt(password.encryptedPassword.password)
}