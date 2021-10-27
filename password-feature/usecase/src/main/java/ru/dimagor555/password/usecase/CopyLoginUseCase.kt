package ru.dimagor555.password.usecase

import ru.dimagor555.password.domain.Password
import ru.dimagor555.password.repository.ClipboardRepository
import ru.dimagor555.password.repository.PasswordRepository

class CopyLoginUseCase(
    passwordRepository: PasswordRepository,
    clipboardRepository: ClipboardRepository
) : CopyUseCase(passwordRepository, clipboardRepository) {
    override fun getTextToCopy(password: Password) = password.encryptedPassword.login
}