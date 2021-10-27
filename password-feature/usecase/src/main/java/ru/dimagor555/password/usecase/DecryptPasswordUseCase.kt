package ru.dimagor555.password.usecase

import ru.dimagor555.encryption.domain.Decryptor
import ru.dimagor555.password.domain.Password

class DecryptPasswordUseCase(
    private val decryptor: Decryptor
) {
    operator fun invoke(password: Password) = decryptor.decrypt(password.encryptedPassword.password)
}