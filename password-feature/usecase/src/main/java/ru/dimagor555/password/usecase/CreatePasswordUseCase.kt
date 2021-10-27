package ru.dimagor555.password.usecase

import ru.dimagor555.encryption.domain.Encryptor
import ru.dimagor555.password.domain.EncryptedPassword
import ru.dimagor555.password.domain.Password
import ru.dimagor555.password.domain.validation.PasswordValidation
import ru.dimagor555.password.repository.PasswordRepository

class CreatePasswordUseCase(
    private val passwordRepository: PasswordRepository,
    private val encryptor: Encryptor
) {
    suspend operator fun invoke(passwordDto: CreatePasswordDto) {
        validate(passwordDto)
        val password = Password(encryptedPassword = createEncryptedPassword(passwordDto))
        passwordRepository.add(password)
    }

    private fun validate(passwordDto: CreatePasswordDto) = with(passwordDto) {
        PasswordValidation(title, login, password).validate()
    }

    private fun createEncryptedPassword(passwordDto: CreatePasswordDto) = EncryptedPassword(
        title = passwordDto.title,
        login = passwordDto.login,
        password = encryptor.encrypt(passwordDto.password)
    )
}

data class CreatePasswordDto(
    val title: String,
    val login: String,
    val password: String
)