package ru.dimagor555.password.usecase.field

import ru.dimagor555.password.domain.password.Password
import ru.dimagor555.password.domain.password.login
import ru.dimagor555.password.usecase.field.repository.ClipboardRepository
import ru.dimagor555.password.usecase.password.repository.PasswordRepository

class CopyLoginUseCase(
    passwordRepository: PasswordRepository,
    clipboardRepository: ClipboardRepository
) : CopyUseCase(passwordRepository, clipboardRepository) {
    override fun getTextToCopy(password: Password): String = password.fields.login!!.text
}