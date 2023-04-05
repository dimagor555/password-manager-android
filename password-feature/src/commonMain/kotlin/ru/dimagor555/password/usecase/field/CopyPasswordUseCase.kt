package ru.dimagor555.password.usecase.field

import ru.dimagor555.encryption.symmetric.domain.SymmetricDecryptor
import ru.dimagor555.password.domain.password.Password
import ru.dimagor555.password.domain.password.encryptedPassword
import ru.dimagor555.password.usecase.field.repository.ClipboardRepository
import ru.dimagor555.password.usecase.password.repository.PasswordRepository

class CopyPasswordUseCase(
    passwordRepository: PasswordRepository,
    clipboardRepository: ClipboardRepository,
    private val decryptor: SymmetricDecryptor
) : CopyUseCase(passwordRepository, clipboardRepository) {
    override fun getTextToCopy(password: Password) = decryptor.decrypt(password.fields.encryptedPassword.text)
}